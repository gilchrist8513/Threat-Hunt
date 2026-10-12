package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*
import com.example.viewmodel.InvestigationTab

@Composable
fun InvestigationPanel(
    host: Host,
    currentTab: InvestigationTab,
    onTabSelected: (InvestigationTab) -> Unit,
    onToggleIsolation: (String) -> Unit,
    onInspectProcess: (ProcessItem) -> Unit,
    onInspectConnection: (NetworkConnection) -> Unit,
    onAddEvidence: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 2.dp)
            .testTag("investigation_panel")
    ) {
        // Host Header & Action Strip
        HostHeaderCard(
            host = host,
            onToggleIsolation = { onToggleIsolation(host.id) }
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Investigation Navigation Tabs
        TabRow(
            selectedTabIndex = currentTab.ordinal,
            containerColor = SocSectionHeader,
            contentColor = SocCyan,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[currentTab.ordinal]),
                    color = SocCyan,
                    height = 2.dp
                )
            },
            divider = {
                HorizontalDivider(color = SocCardBorder)
            },
            modifier = Modifier.clip(RoundedCornerShape(4.dp))
        ) {
            InvestigationTab.values().forEach { tab ->
                Tab(
                    selected = currentTab == tab,
                    onClick = { onTabSelected(tab) },
                    text = {
                        Text(
                            text = tab.title.uppercase(),
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (currentTab == tab) FontWeight.Bold else FontWeight.Medium,
                            color = if (currentTab == tab) Color.White else SocTextMuted
                        )
                    },
                    modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Tab Content Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (currentTab) {
                InvestigationTab.OVERVIEW -> HostOverviewTab(host, onAddEvidence)
                InvestigationTab.PROCESSES -> HostProcessesTab(host.processes, onInspectProcess)
                InvestigationTab.NETWORK -> HostNetworkTab(host.networkConnections, onInspectConnection)
                InvestigationTab.AUTH_LOGS -> HostAuthLogsTab(host.authEvents, onAddEvidence)
                InvestigationTab.FILES -> HostFilesTab(host.fileArtifacts, onAddEvidence)
            }
        }
    }
}

@Composable
fun HostHeaderCard(
    host: Host,
    onToggleIsolation: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, if (host.isIsolated) SocAmber else SocCardBorder, RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(containerColor = SocSurface),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            // Sleek Section Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SocSectionHeader)
                    .border(1.dp, SocCardBorder, RoundedCornerShape(0.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "INVESTIGATION: ${host.hostname}",
                    color = SocTextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${host.ipAddress} • ${host.domain}",
                    color = SocBlue,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "User: ${host.currentUser}  [${host.role}]",
                        color = SocTextPrimary,
                        fontSize = 10.5.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "OS: ${host.os} • MAC: ${host.macAddress}",
                        color = SocTextMuted,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Isolate Host Simulated Action Button
                Button(
                    onClick = onToggleIsolation,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (host.isIsolated) SocAmber else SocRedDim
                    ),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("isolate_host_button")
                ) {
                    Icon(
                        imageVector = if (host.isIsolated) Icons.Default.LockOpen else Icons.Default.Lock,
                        contentDescription = "Isolate Action",
                        tint = if (host.isIsolated) Color.Black else Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (host.isIsolated) "UNISOLATE" else "ISOLATE HOST",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = if (host.isIsolated) Color.Black else Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun HostOverviewTab(host: Host, onAddEvidence: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SocSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp)),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SocSectionHeader)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "HOST TELEMETRY SUMMARY",
                            color = SocTextMuted,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = host.description,
                            color = SocTextPrimary,
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = SocCardBorder)
                        Spacer(modifier = Modifier.height(8.dp))

                        DetailRow("Assigned Role", host.role)
                        DetailRow("MAC Address", host.macAddress)
                        DetailRow("Active Domain", host.domain)
                        DetailRow("Network Isolation Status", if (host.isIsolated) "ISOLATED (CONTAINED)" else "UNRESTRICTED (LIVE)")
                        DetailRow("Active Alerts", "${host.alertCount} Detected", isThreat = host.alertCount > 0)

                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                onAddEvidence("Host Triage: ${host.hostname} (${host.ipAddress}) - User: ${host.currentUser} - Role: ${host.role}")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SocSlateButton),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, SocCardBorderHighlight, RoundedCornerShape(4.dp))
                        ) {
                            Text("Add Host Info to Notes", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = SocTextPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HostProcessesTab(
    processes: List<ProcessItem>,
    onInspectProcess: (ProcessItem) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Section Header Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ACTIVE PROCESS TREE",
                    color = SocTextMuted,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "0x15F2",
                    color = SocBlue,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        items(processes) { process ->
            val isMalicious = process.isSuspicious && !process.isTerminated
            val isChildProcess = process.parentPid == 644 || process.parentPid == 912 || process.parentPid == 3820

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (isMalicious) SocRedBorder else SocCardBorder,
                        RoundedCornerShape(4.dp)
                    )
                    .clickable { onInspectProcess(process) }
                    .testTag("process_row_${process.pid}"),
                colors = CardDefaults.cardColors(
                    containerColor = if (isMalicious) SocRedDim.copy(alpha = 0.25f)
                    else SocSurface
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (isChildProcess) "└─ ${process.name}" else process.name,
                                color = if (isMalicious) SocRedLight else Color.White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.5.sp
                            )
                            Text(
                                text = "PID: ${process.pid}",
                                color = SocBlue,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp
                            )
                            if (process.isTerminated) {
                                Text(
                                    text = "[TERMINATED]",
                                    color = SocTextMuted,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            } else if (process.isSuspicious) {
                                Text(
                                    text = "[SUSPICIOUS]",
                                    color = SocRed,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = "Parent: ${process.parentName} (${process.parentPid}) • User: ${process.user}",
                            color = SocTextSecondary,
                            fontSize = 9.5.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Sleek Action Button
                    Button(
                        onClick = { onInspectProcess(process) },
                        colors = ButtonDefaults.buttonColors(containerColor = SocSlateButton),
                        shape = RoundedCornerShape(3.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier
                            .border(1.dp, SocCardBorderHighlight, RoundedCornerShape(3.dp))
                            .testTag("inspect_process_${process.pid}")
                    ) {
                        Text(
                            text = "INSPECT",
                            fontSize = 9.5.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = SocTextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HostNetworkTab(
    connections: List<NetworkConnection>,
    onInspectConnection: (NetworkConnection) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "NETWORK ACTIVITY",
                    color = SocTextMuted,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "${connections.size} SOCKETS",
                    color = SocBlue,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        items(connections) { conn ->
            val isSuspicious = conn.isSuspicious

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (isSuspicious) SocRedBorder else SocCardBorder,
                        RoundedCornerShape(4.dp)
                    )
                    .clickable { onInspectConnection(conn) }
                    .testTag("network_row_${conn.id}"),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSuspicious) SocRedDim.copy(alpha = 0.25f)
                    else SocSurface
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "${conn.remoteAddress}:${conn.remotePort}",
                                color = if (isSuspicious) SocRedLight else Color.White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.5.sp
                            )
                            if (isSuspicious) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(SocRedDim.copy(alpha = 0.6f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "C2_SUSPECTED",
                                        color = SocRedLight,
                                        fontSize = 8.5.sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Text(
                                text = "(${conn.state})",
                                color = if (conn.state.contains("SEVERED")) SocAmber else SocTextMuted,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp
                            )
                        }

                        Text(
                            text = "Proc: ${conn.processName} • Domain: ${conn.domainName ?: "N/A"}",
                            color = SocTextSecondary,
                            fontSize = 9.5.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Button(
                        onClick = { onInspectConnection(conn) },
                        colors = ButtonDefaults.buttonColors(containerColor = SocSlateButton),
                        shape = RoundedCornerShape(3.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.border(1.dp, SocCardBorderHighlight, RoundedCornerShape(3.dp))
                    ) {
                        Text(
                            text = "TRACE",
                            fontSize = 9.5.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = SocTextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HostAuthLogsTab(
    authEvents: List<AuthEvent>,
    onAddEvidence: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(authEvents) { event ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (event.isSuspicious) SocRedBorder else SocCardBorder,
                        RoundedCornerShape(4.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (event.isSuspicious) SocRedDim.copy(alpha = 0.2f)
                    else SocSurface
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Event ID: ${event.eventId} - ${event.eventName}",
                            color = if (event.isSuspicious) SocRedLight else Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                        Text(
                            text = event.timestamp,
                            color = SocTextMuted,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = event.details,
                        color = SocTextSecondary,
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "User: ${event.username} • Source: ${event.sourceIp}",
                            color = SocTextMuted,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )

                        if (event.isSuspicious) {
                            TextButton(
                                onClick = {
                                    onAddEvidence("Auth Event ${event.eventId}: ${event.eventName} - User: ${event.username} - Details: ${event.details}")
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Pin to Notes", fontSize = 9.sp, color = SocCyan, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HostFilesTab(
    files: List<FileArtifact>,
    onAddEvidence: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(files) { file ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (file.isSuspicious) SocRedBorder else SocCardBorder,
                        RoundedCornerShape(4.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (file.isSuspicious) SocRedDim.copy(alpha = 0.2f)
                    else SocSurface
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = file.fileName,
                            color = if (file.isSuspicious) SocRedLight else Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                        Text(
                            text = file.size,
                            color = SocTextMuted,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = file.path,
                        color = SocTextSecondary,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.5.sp
                    )

                    if (file.threatNotes != null) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = file.threatNotes,
                            color = if (file.isSuspicious) SocAmber else SocTextMuted,
                            fontSize = 9.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SHA-256: ${file.sha256.take(16)}...",
                            color = SocTextMuted,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 8.5.sp
                        )

                        TextButton(
                            onClick = {
                                onAddEvidence("File Artifact: ${file.fileName}\nPath: ${file.path}\nSHA-256: ${file.sha256}")
                            },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Pin Hash", fontSize = 9.sp, color = SocCyan, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, isThreat: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = SocTextMuted,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = value,
            color = if (isThreat) SocRedLight else SocTextPrimary,
            fontSize = 10.5.sp,
            fontWeight = if (isThreat) FontWeight.Bold else FontWeight.Medium,
            fontFamily = FontFamily.Monospace
        )
    }
}
