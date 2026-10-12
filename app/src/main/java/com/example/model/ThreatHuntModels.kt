package com.example.model

enum class ThreatSeverity(val label: String) {
    CRITICAL("CRITICAL"),
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW"),
    INFO("INFO"),
    CLEAN("CLEAN")
}

data class Host(
    val id: String,
    val hostname: String,
    val ipAddress: String,
    val macAddress: String,
    val os: String,
    val domain: String,
    val currentUser: String,
    val role: String,
    val isIsolated: Boolean = false,
    val alertCount: Int = 0,
    val threatSeverity: ThreatSeverity = ThreatSeverity.CLEAN,
    val description: String,
    val processes: List<ProcessItem> = emptyList(),
    val networkConnections: List<NetworkConnection> = emptyList(),
    val authEvents: List<AuthEvent> = emptyList(),
    val fileArtifacts: List<FileArtifact> = emptyList()
)

data class ProcessItem(
    val pid: Int,
    val name: String,
    val parentPid: Int,
    val parentName: String,
    val user: String,
    val commandLine: String,
    val decodedCommandLine: String? = null,
    val path: String,
    val cpuUsage: String,
    val memoryUsage: String,
    val startTime: String,
    val isSuspicious: Boolean,
    val threatScore: String,
    val hash: String,
    val isTerminated: Boolean = false
)

data class NetworkConnection(
    val id: String,
    val localAddress: String,
    val localPort: Int,
    val remoteAddress: String,
    val remotePort: Int,
    val protocol: String,
    val state: String,
    val processName: String,
    val pid: Int,
    val domainName: String? = null,
    val geoIp: String? = null,
    val threatTag: String? = null,
    val isSuspicious: Boolean = false,
    val timestamp: String
)

data class AuthEvent(
    val id: String,
    val eventId: Int,
    val eventName: String,
    val timestamp: String,
    val username: String,
    val sourceIp: String,
    val logonType: String,
    val status: String,
    val isSuspicious: Boolean = false,
    val details: String
)

data class FileArtifact(
    val path: String,
    val fileName: String,
    val size: String,
    val createdTime: String,
    val modifiedTime: String,
    val sha256: String,
    val isSuspicious: Boolean,
    val fileType: String,
    val threatNotes: String? = null
)

data class AlertItem(
    val id: String,
    val title: String,
    val hostId: String,
    val hostname: String,
    val severity: ThreatSeverity,
    val category: String,
    val mitreTactic: String,
    val mitreTechniqueId: String,
    val description: String,
    val timestamp: String,
    val isInvestigated: Boolean = false,
    val isBookmarkedEvidence: Boolean = false,
    val recommendation: String
)

data class TimelineEvent(
    val id: String,
    val timestamp: String,
    val hostId: String,
    val hostname: String,
    val source: String,
    val eventType: String,
    val severity: ThreatSeverity,
    val summary: String,
    val details: String
)

data class InvestigationNote(
    val id: String,
    val timestamp: String,
    val text: String,
    val tags: List<String> = emptyList()
)
