package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.model.Host
import com.example.model.ThreatSeverity
import com.example.ui.theme.*

@Composable
fun HostsPanel(
    hosts: List<Host>,
    selectedHostId: String,
    onSelectHost: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SocSurface)
            .border(1.dp, SocCardBorder, RoundedCornerShape(0.dp))
    ) {
        // Section Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SocSectionHeader)
                .border(1.dp, SocCardBorder, RoundedCornerShape(0.dp))
                .padding(horizontal = 12.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "HOSTS",
                color = SocTextMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp
            )
            Text(
                text = "${hosts.size} MONITORED",
                color = SocTextMuted,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        // Horizontal scrolling host cards
        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(hosts) { host ->
                HostCard(
                    host = host,
                    isSelected = host.id == selectedHostId,
                    onClick = { onSelectHost(host.id) }
                )
            }
        }
    }
}

@Composable
fun HostCard(
    host: Host,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val isCritical = host.threatSeverity == ThreatSeverity.CRITICAL && !host.isIsolated
    val isIsolated = host.isIsolated

    val borderColor = when {
        isSelected -> SocCyan
        isCritical -> SocRedBorder
        isIsolated -> SocAmber
        else -> SocCardBorder
    }

    val backgroundColor = when {
        isSelected -> SocSurfaceVariant
        isCritical -> SocRedDim.copy(alpha = 0.25f)
        else -> SocCardBackground
    }

    val statusDotColor = when {
        isCritical -> SocRed
        isIsolated -> SocAmber
        else -> SocTerminalGreen
    }

    Box(
        modifier = Modifier
            .width(125.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(
                width = if (isSelected) 1.5.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 7.dp)
            .testTag("host_card_${host.hostname.lowercase()}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = host.hostname,
                    color = if (isCritical) SocRedLight else if (isSelected) Color.White else SocTextPrimary,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = host.ipAddress,
                    color = SocTextMuted,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            // Status Indicator Dot
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(statusDotColor)
            )
        }
    }
}

