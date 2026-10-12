package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import androidx.compose.ui.window.Dialog
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun ProcessInspectDialog(
    process: ProcessItem,
    onDismiss: () -> Unit,
    onTerminate: (Int) -> Unit,
    onAddEvidence: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(6.dp))
                .border(1.dp, if (process.isSuspicious) SocRedBorder else SocCardBorder, RoundedCornerShape(6.dp))
                .testTag("process_inspect_dialog"),
            color = SocSurface
        ) {
            Column(
                modifier = Modifier
                    .padding(14.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = null,
                            tint = if (process.isSuspicious) SocRedLight else SocCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "PROCESS INSPECTION",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(22.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SocTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Status Badge
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (process.isSuspicious) SocRedDim.copy(alpha = 0.4f)
                            else SocSectionHeader
                        )
                        .border(1.dp, if (process.isSuspicious) SocRedBorder else SocCardBorder, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = process.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "PID: ${process.pid}",
                        color = SocBlue,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Detail Fields
                ModalDetailRow("Parent Process", "${process.parentName} (PID: ${process.parentPid})")
                ModalDetailRow("Executed By User", process.user)
                ModalDetailRow("Image Path", process.path)
                ModalDetailRow("Start Time", process.startTime)
                ModalDetailRow("Memory / CPU", "${process.memoryUsage} / ${process.cpuUsage}")
                ModalDetailRow("Threat Score", process.threatScore, isThreat = process.isSuspicious)

                Spacer(modifier = Modifier.height(6.dp))

                // Command Line Box
                Text(
                    text = "COMMAND LINE ARGUMENTS:",
                    color = SocTextMuted,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(SocCodeBackground)
                        .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp))
                        .padding(6.dp)
                ) {
                    Text(
                        text = process.commandLine,
                        color = if (process.isSuspicious) SocAmber else SocTerminalGreen,
                        fontSize = 9.5.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Decoded Payload if available
                if (process.decodedCommandLine != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "DECODED BASE64 PAYLOAD:",
                        color = SocRedLight,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(SocRedDim.copy(alpha = 0.25f))
                            .border(1.dp, SocRedBorder, RoundedCornerShape(4.dp))
                            .padding(6.dp)
                    ) {
                        Text(
                            text = process.decodedCommandLine,
                            color = SocRedLight,
                            fontSize = 9.5.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Hash
                ModalDetailRow("SHA-256 Hash", process.hash)

                Spacer(modifier = Modifier.height(12.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Button(
                        onClick = {
                            onAddEvidence("Process: ${process.name} (PID: ${process.pid}) - Threat: ${process.threatScore}\nHash: ${process.hash}")
                        },
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, SocCardBorderHighlight, RoundedCornerShape(4.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = SocSlateButton),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("Add to Notes", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = SocTextPrimary)
                    }

                    if (process.isSuspicious && !process.isTerminated) {
                        Button(
                            onClick = { onTerminate(process.pid) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("terminate_process_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = SocRed),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("Kill Process", fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NetworkInspectDialog(
    connection: NetworkConnection,
    onDismiss: () -> Unit,
    onAddEvidence: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(6.dp))
                .border(1.dp, if (connection.isSuspicious) SocRedBorder else SocCardBorder, RoundedCornerShape(6.dp))
                .testTag("network_inspect_dialog"),
            color = SocSurface
        ) {
            Column(
                modifier = Modifier
                    .padding(14.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = if (connection.isSuspicious) SocRedLight else SocCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "NETWORK TELEMETRY",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(22.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SocTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                ModalDetailRow("Socket", "${connection.localAddress}:${connection.localPort} -> ${connection.remoteAddress}:${connection.remotePort}")
                ModalDetailRow("Protocol / State", "${connection.protocol} / ${connection.state}")
                ModalDetailRow("Associated Process", "${connection.processName} (PID: ${connection.pid})")
                if (connection.domainName != null) {
                    ModalDetailRow("Domain Name", connection.domainName)
                }
                if (connection.geoIp != null) {
                    ModalDetailRow("GeoIP / ASN Info", connection.geoIp)
                }
                if (connection.threatTag != null) {
                    ModalDetailRow("Threat Classification", connection.threatTag, isThreat = connection.isSuspicious)
                }
                ModalDetailRow("Observed Timestamp", connection.timestamp)

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        onAddEvidence("Network IOC: ${connection.remoteAddress}:${connection.remotePort} (${connection.domainName ?: "No Domain"}) - Process: ${connection.processName}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SocCardBorderHighlight, RoundedCornerShape(4.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = SocSlateButton),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Add Network IOC to Notes", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = SocTextPrimary)
                }
            }
        }
    }
}

@Composable
fun AlertInspectDialog(
    alert: AlertItem,
    onDismiss: () -> Unit,
    onBookmark: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(6.dp))
                .border(1.dp, SocRedBorder, RoundedCornerShape(6.dp))
                .testTag("alert_inspect_dialog"),
            color = SocSurface
        ) {
            Column(
                modifier = Modifier
                    .padding(14.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = SocRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = alert.id,
                            color = SocRedLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(22.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SocTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = alert.title,
                    color = Color.White,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                ModalDetailRow("Target Host", alert.hostname)
                ModalDetailRow("Severity", alert.severity.name, isThreat = true)
                ModalDetailRow("MITRE ATT&CK", alert.mitreTactic)
                ModalDetailRow("Detected Time", alert.timestamp)

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "DETECTION SUMMARY:",
                    color = SocTextMuted,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(SocSectionHeader)
                        .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp))
                        .padding(6.dp)
                ) {
                    Text(
                        text = alert.description,
                        color = SocTextPrimary,
                        fontSize = 10.5.sp,
                        lineHeight = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "RECOMMENDED ACTION:",
                    color = SocBlue,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(3.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(SocBlue.copy(alpha = 0.1f))
                        .border(1.dp, SocBlue.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                        .padding(6.dp)
                ) {
                    Text(
                        text = alert.recommendation,
                        color = Color.White,
                        fontSize = 10.5.sp,
                        lineHeight = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        onBookmark(alert.id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SocCardBorderHighlight, RoundedCornerShape(4.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (alert.isBookmarkedEvidence) SocEmeraldDim else SocSlateButton
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = if (alert.isBookmarkedEvidence) "Saved to Evidence Notes" else "Tag as Pinned Evidence",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = SocTextPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun ModalDetailRow(label: String, value: String, isThreat: Boolean = false) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            text = label.uppercase(),
            color = SocTextMuted,
            fontSize = 8.5.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            color = if (isThreat) SocRedLight else SocTextPrimary,
            fontSize = 10.5.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
