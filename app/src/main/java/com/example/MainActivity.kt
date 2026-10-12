package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Host
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.ThreatHuntViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ThreatHuntViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ThreatHuntApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ThreatHuntApp(viewModel: ThreatHuntViewModel) {
    val hosts by viewModel.hosts.collectAsState()
    val selectedHostId by viewModel.selectedHostId.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val alerts by viewModel.alerts.collectAsState()
    val timelineEvents by viewModel.timelineEvents.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val newNoteText by viewModel.newNoteText.collectAsState()
    val activePanel by viewModel.activePanel.collectAsState()

    val inspectedProcess by viewModel.inspectedProcess.collectAsState()
    val inspectedConnection by viewModel.inspectedConnection.collectAsState()
    val inspectedAlert by viewModel.inspectedAlert.collectAsState()

    val selectedHost = hosts.find { it.id == selectedHostId } ?: hosts.first()
    val isThreatActive = viewModel.isThreatActive()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(SocBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        bottomBar = {
            NavigationBar(
                containerColor = SocSectionHeader,
                contentColor = SocCyan,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("soc_bottom_navigation")
            ) {
                NavigationBarItem(
                    selected = activePanel == "INVESTIGATION",
                    onClick = { viewModel.setActivePanel("INVESTIGATION") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Investigation"
                        )
                    },
                    label = {
                        Text(
                            "INVESTIGATE",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            fontWeight = if (activePanel == "INVESTIGATION") FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SocCyan,
                        selectedTextColor = SocCyan,
                        unselectedIconColor = SocTextMuted,
                        unselectedTextColor = SocTextMuted,
                        indicatorColor = SocSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_investigate")
                )

                NavigationBarItem(
                    selected = activePanel == "ALERTS",
                    onClick = { viewModel.setActivePanel("ALERTS") },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (alerts.isNotEmpty()) {
                                    Badge(
                                        containerColor = SocRed,
                                        contentColor = Color.White
                                    ) {
                                        Text(
                                            text = alerts.size.toString(),
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Alerts"
                            )
                        }
                    },
                    label = {
                        Text(
                            "ALERTS",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            fontWeight = if (activePanel == "ALERTS") FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SocRedLight,
                        selectedTextColor = SocRedLight,
                        unselectedIconColor = SocTextMuted,
                        unselectedTextColor = SocTextMuted,
                        indicatorColor = SocSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_alerts")
                )

                NavigationBarItem(
                    selected = activePanel == "TIMELINE",
                    onClick = { viewModel.setActivePanel("TIMELINE") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Timeline"
                        )
                    },
                    label = {
                        Text(
                            "TIMELINE",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            fontWeight = if (activePanel == "TIMELINE") FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SocCyan,
                        selectedTextColor = SocCyan,
                        unselectedIconColor = SocTextMuted,
                        unselectedTextColor = SocTextMuted,
                        indicatorColor = SocSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_timeline")
                )

                NavigationBarItem(
                    selected = activePanel == "NOTES",
                    onClick = { viewModel.setActivePanel("NOTES") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Notes"
                        )
                    },
                    label = {
                        Text(
                            "NOTES",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            fontWeight = if (activePanel == "NOTES") FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SocCyan,
                        selectedTextColor = SocCyan,
                        unselectedIconColor = SocTextMuted,
                        unselectedTextColor = SocTextMuted,
                        indicatorColor = SocSurfaceVariant
                    ),
                    modifier = Modifier.testTag("nav_notes")
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(SocBackground)
        ) {
            // Top SOC Header with "THREAT HUNT" and "THREAT ACTIVE" indicator
            SocHeader(
                isThreatActive = isThreatActive,
                onReset = { viewModel.resetSimulation() }
            )

            // Hosts Panel (FIN-WS01, FIN-WS02, HR-WS01, EXEC-WS01, WEB-01, DC-01)
            HostsPanel(
                hosts = hosts,
                selectedHostId = selectedHostId,
                onSelectHost = { hostId ->
                    viewModel.selectHost(hostId)
                    viewModel.setActivePanel("INVESTIGATION")
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Main Active Panel Content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (activePanel) {
                    "INVESTIGATION" -> {
                        InvestigationPanel(
                            host = selectedHost,
                            currentTab = currentTab,
                            onTabSelected = { viewModel.selectTab(it) },
                            onToggleIsolation = { viewModel.toggleHostIsolation(it) },
                            onInspectProcess = { viewModel.inspectProcess(it) },
                            onInspectConnection = { viewModel.inspectConnection(it) },
                            onAddEvidence = { viewModel.addEvidenceSnippet(it) }
                        )
                    }

                    "ALERTS" -> {
                        AlertsEvidencePanel(
                            alerts = alerts,
                            onSelectAlert = { viewModel.inspectAlert(it) },
                            onToggleBookmark = { viewModel.toggleEvidenceBookmark(it) },
                            onNavigateToHost = { hostId ->
                                viewModel.selectHost(hostId)
                                viewModel.setActivePanel("INVESTIGATION")
                            }
                        )
                    }

                    "TIMELINE" -> {
                        TimelinePanel(
                            timelineEvents = timelineEvents,
                            onAddEvidence = { viewModel.addEvidenceSnippet(it) }
                        )
                    }

                    "NOTES" -> {
                        NotesPanel(
                            notes = notes,
                            newNoteText = newNoteText,
                            onNoteChange = { viewModel.updateNewNoteText(it) },
                            onSaveNote = { viewModel.saveNote() },
                            onDeleteNote = { viewModel.deleteNote(it) }
                        )
                    }
                }
            }
        }

        // Inspection Dialogs
        inspectedProcess?.let { proc ->
            ProcessInspectDialog(
                process = proc,
                onDismiss = { viewModel.inspectProcess(null) },
                onTerminate = { viewModel.terminateProcess(it) },
                onAddEvidence = { viewModel.addEvidenceSnippet(it) }
            )
        }

        inspectedConnection?.let { conn ->
            NetworkInspectDialog(
                connection = conn,
                onDismiss = { viewModel.inspectConnection(null) },
                onAddEvidence = { viewModel.addEvidenceSnippet(it) }
            )
        }

        inspectedAlert?.let { alert ->
            AlertInspectDialog(
                alert = alert,
                onDismiss = { viewModel.inspectAlert(null) },
                onBookmark = { viewModel.toggleEvidenceBookmark(it) }
            )
        }
    }
}
