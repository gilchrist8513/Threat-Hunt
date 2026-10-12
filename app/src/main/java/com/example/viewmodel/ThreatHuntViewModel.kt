package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SimulatedThreatData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class InvestigationTab(val title: String) {
    OVERVIEW("Overview"),
    PROCESSES("Processes"),
    NETWORK("Network"),
    AUTH_LOGS("Auth Logs"),
    FILES("Files")
}

class ThreatHuntViewModel : ViewModel() {

    private val _hosts = MutableStateFlow<List<Host>>(SimulatedThreatData.getInitialHosts())
    val hosts: StateFlow<List<Host>> = _hosts.asStateFlow()

    private val _selectedHostId = MutableStateFlow("host-fin-ws01")
    val selectedHostId: StateFlow<String> = _selectedHostId.asStateFlow()

    private val _currentTab = MutableStateFlow(InvestigationTab.OVERVIEW)
    val currentTab: StateFlow<InvestigationTab> = _currentTab.asStateFlow()

    private val _alerts = MutableStateFlow<List<AlertItem>>(SimulatedThreatData.getInitialAlerts())
    val alerts: StateFlow<List<AlertItem>> = _alerts.asStateFlow()

    private val _timelineEvents = MutableStateFlow<List<TimelineEvent>>(SimulatedThreatData.getInitialTimelineEvents())
    val timelineEvents: StateFlow<List<TimelineEvent>> = _timelineEvents.asStateFlow()

    private val _notes = MutableStateFlow<List<InvestigationNote>>(SimulatedThreatData.getInitialNotes())
    val notes: StateFlow<List<InvestigationNote>> = _notes.asStateFlow()

    private val _newNoteText = MutableStateFlow("")
    val newNoteText: StateFlow<String> = _newNoteText.asStateFlow()

    // Inspection dialogs state
    private val _inspectedProcess = MutableStateFlow<ProcessItem?>(null)
    val inspectedProcess: StateFlow<ProcessItem?> = _inspectedProcess.asStateFlow()

    private val _inspectedConnection = MutableStateFlow<NetworkConnection?>(null)
    val inspectedConnection: StateFlow<NetworkConnection?> = _inspectedConnection.asStateFlow()

    private val _inspectedAlert = MutableStateFlow<AlertItem?>(null)
    val inspectedAlert: StateFlow<AlertItem?> = _inspectedAlert.asStateFlow()

    private val _activePanel = MutableStateFlow("INVESTIGATION") // "INVESTIGATION", "ALERTS", "TIMELINE", "NOTES"
    val activePanel: StateFlow<String> = _activePanel.asStateFlow()

    private fun getCurrentTimestamp(): String {
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
    }

    fun selectHost(hostId: String) {
        _selectedHostId.value = hostId
    }

    fun selectTab(tab: InvestigationTab) {
        _currentTab.value = tab
    }

    fun setActivePanel(panel: String) {
        _activePanel.value = panel
    }

    fun inspectProcess(process: ProcessItem?) {
        _inspectedProcess.value = process
    }

    fun inspectConnection(connection: NetworkConnection?) {
        _inspectedConnection.value = connection
    }

    fun inspectAlert(alert: AlertItem?) {
        _inspectedAlert.value = alert
        if (alert != null) {
            // Also select the host associated with this alert
            _selectedHostId.value = alert.hostId
            // Mark alert as investigated
            _alerts.value = _alerts.value.map {
                if (it.id == alert.id) it.copy(isInvestigated = true) else it
            }
        }
    }

    fun toggleHostIsolation(hostId: String) {
        val currentHosts = _hosts.value
        val host = currentHosts.find { it.id == hostId } ?: return
        val newIsolatedState = !host.isIsolated
        val time = getCurrentTimestamp()

        _hosts.value = currentHosts.map { h ->
            if (h.id == hostId) {
                // If isolated, update connections to severed and reduce threat severity
                val updatedConnections = if (newIsolatedState) {
                    h.networkConnections.map { conn ->
                        if (conn.isSuspicious) conn.copy(state = "SEVERED_BY_SOC", threatTag = "TRAFFIC BLOCKED") else conn
                    }
                } else {
                    h.networkConnections
                }
                h.copy(
                    isIsolated = newIsolatedState,
                    networkConnections = updatedConnections,
                    threatSeverity = if (newIsolatedState) ThreatSeverity.MEDIUM else ThreatSeverity.CRITICAL
                )
            } else h
        }

        // Add timeline event
        val logEvent = TimelineEvent(
            id = "action-${UUID.randomUUID()}",
            timestamp = time,
            hostId = hostId,
            hostname = host.hostname,
            source = "SOC Analyst Console",
            eventType = if (newIsolatedState) "HOST ISOLATION APPLIED" else "HOST ISOLATION REMOVED",
            severity = if (newIsolatedState) ThreatSeverity.HIGH else ThreatSeverity.INFO,
            summary = if (newIsolatedState) "Analyst applied host network isolation to ${host.hostname}" else "Analyst lifted network isolation from ${host.hostname}",
            details = if (newIsolatedState) "All inbound and outbound non-management packets dropped. Active C2 sockets severed." else "Normal network routing restored."
        )
        _timelineEvents.value = listOf(logEvent) + _timelineEvents.value
    }

    fun terminateProcess(pid: Int) {
        val selectedId = _selectedHostId.value
        val time = getCurrentTimestamp()
        var processName = "Process"

        _hosts.value = _hosts.value.map { host ->
            if (host.id == selectedId) {
                val updatedProcesses = host.processes.map { proc ->
                    if (proc.pid == pid) {
                        processName = proc.name
                        proc.copy(isTerminated = true, threatScore = "0.0 (TERMINATED)")
                    } else proc
                }
                host.copy(processes = updatedProcesses)
            } else host
        }

        // Add timeline event
        val logEvent = TimelineEvent(
            id = "kill-${UUID.randomUUID()}",
            timestamp = time,
            hostId = selectedId,
            hostname = _hosts.value.find { it.id == selectedId }?.hostname ?: "HOST",
            source = "SOC EDR Remediation",
            eventType = "PROCESS TERMINATED",
            severity = ThreatSeverity.MEDIUM,
            summary = "Terminated suspicious process $processName (PID: $pid)",
            details = "Process tree killed via EDR kernel driver. Associated threads cleaned up."
        )
        _timelineEvents.value = listOf(logEvent) + _timelineEvents.value

        // Close inspect dialog
        _inspectedProcess.value = null
    }

    fun toggleEvidenceBookmark(alertId: String) {
        _alerts.value = _alerts.value.map {
            if (it.id == alertId) {
                val newBookmarked = !it.isBookmarkedEvidence
                if (newBookmarked) {
                    // Auto-append to notes as evidence
                    addEvidenceSnippet("[ALERT EVIDENCE] ${it.id} (${it.hostname}): ${it.title} - MITRE: ${it.mitreTactic}")
                }
                it.copy(isBookmarkedEvidence = newBookmarked)
            } else it
        }
    }

    fun addEvidenceSnippet(snippet: String) {
        val existing = _newNoteText.value
        val updated = if (existing.isBlank()) {
            snippet
        } else {
            "$existing\n• $snippet"
        }
        _newNoteText.value = updated
    }

    fun updateNewNoteText(text: String) {
        _newNoteText.value = text
    }

    fun saveNote() {
        val text = _newNoteText.value.trim()
        if (text.isEmpty()) return

        val newNote = InvestigationNote(
            id = "note-${UUID.randomUUID()}",
            timestamp = getCurrentTimestamp(),
            text = text,
            tags = listOf("Triage", _hosts.value.find { it.id == _selectedHostId.value }?.hostname ?: "General")
        )
        _notes.value = listOf(newNote) + _notes.value
        _newNoteText.value = ""
    }

    fun deleteNote(noteId: String) {
        _notes.value = _notes.value.filter { it.id != noteId }
    }

    fun isThreatActive(): Boolean {
        // Active if FIN-WS01 is not isolated and has unterminated suspicious processes
        val finWs01 = _hosts.value.find { it.hostname == "FIN-WS01" }
        return finWs01?.isIsolated == false
    }

    fun resetSimulation() {
        _hosts.value = SimulatedThreatData.getInitialHosts()
        _alerts.value = SimulatedThreatData.getInitialAlerts()
        _timelineEvents.value = SimulatedThreatData.getInitialTimelineEvents()
        _notes.value = SimulatedThreatData.getInitialNotes()
        _selectedHostId.value = "host-fin-ws01"
        _currentTab.value = InvestigationTab.OVERVIEW
        _newNoteText.value = ""
        _inspectedProcess.value = null
        _inspectedConnection.value = null
        _inspectedAlert.value = null
    }
}
