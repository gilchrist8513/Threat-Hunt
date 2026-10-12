package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.model.InvestigationNote
import com.example.ui.theme.*

@Composable
fun NotesPanel(
    notes: List<InvestigationNote>,
    newNoteText: String,
    onNoteChange: (String) -> Unit,
    onSaveNote: () -> Unit,
    onDeleteNote: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 2.dp)
            .testTag("investigation_notes_panel")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SocSectionHeader)
                .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.EditNote,
                    contentDescription = null,
                    tint = SocCyan,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "INVESTIGATION LOGS & NOTES",
                    color = SocTextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
            }

            Text(
                text = "${notes.size} NOTES LOGGED",
                color = SocTextMuted,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // New Note Input Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = SocSurface),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = newNoteText,
                    onValueChange = onNoteChange,
                    placeholder = {
                        Text(
                            "Record observations, suspect IOCs, process analysis, and remediation steps...",
                            color = SocTextMuted,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(85.dp)
                        .testTag("note_input_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SocCyan,
                        unfocusedBorderColor = SocCardBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = SocCyan
                    ),
                    shape = RoundedCornerShape(4.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Quick Quick-fill chips
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        FilterChip(
                            selected = false,
                            onClick = {
                                val text = if (newNoteText.isBlank()) "IOC: 185.220.101.5 (C2 IP)" else "$newNoteText\nIOC: 185.220.101.5 (C2 IP)"
                                onNoteChange(text)
                            },
                            label = { Text("+ C2 IOC", fontSize = 8.5.sp, fontFamily = FontFamily.Monospace) },
                            colors = FilterChipDefaults.filterChipColors(labelColor = SocBlue)
                        )
                    }

                    Button(
                        onClick = onSaveNote,
                        enabled = newNoteText.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = SocSlateButton),
                        shape = RoundedCornerShape(3.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier
                            .border(1.dp, SocCardBorderHighlight, RoundedCornerShape(3.dp))
                            .testTag("save_note_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            tint = SocCyan,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("SAVE NOTE", fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = SocTextPrimary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Notes List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SocCardBorder, RoundedCornerShape(4.dp)),
                    colors = CardDefaults.cardColors(containerColor = SocSurface),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = note.timestamp,
                                color = SocBlue,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )

                            IconButton(
                                onClick = { onDeleteNote(note.id) },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Note",
                                    tint = SocTextMuted,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = note.text,
                            color = SocTextPrimary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            fontFamily = FontFamily.Monospace
                        )

                        if (note.tags.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                note.tags.forEach { tag ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(SocSectionHeader)
                                            .border(1.dp, SocCardBorder, RoundedCornerShape(2.dp))
                                            .padding(horizontal = 5.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = "#$tag",
                                            color = SocTextSecondary,
                                            fontSize = 8.5.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
