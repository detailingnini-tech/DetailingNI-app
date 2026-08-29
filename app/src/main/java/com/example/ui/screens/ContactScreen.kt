package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DetailingPackages
import com.example.data.model.ServicePackage
import com.example.ui.components.DetailingHeader
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanDark
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.GoldHero
import com.example.ui.theme.GreenSuccess
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.net.URLEncoder

const val BUSINESS_PHONE = "07721326149"
const val BUSINESS_PHONE_INTL = "447721326149"
const val BUSINESS_EMAIL = "valetworx@gmail.com"
const val BUSINESS_ADDRESS = "129 Quarry Heights, Newtownards, BT23 7SZ"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    initialPackage: ServicePackage? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var clientName by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var vehicleModel by remember { mutableStateOf("") }
    var vehicleReg by remember { mutableStateOf("") }
    var selectedPackageName by remember {
        mutableStateOf(initialPackage?.name ?: "Silver Ceramic (Most Popular)")
    }
    var customerMessage by remember { mutableStateOf("") }
    var packageDropdownExpanded by remember { mutableStateOf(false) }

    val packageOptions = listOf(
        "Silver Ceramic (£625 - Most Popular)",
        "Gold Graphene (£950 - Hero Package)",
        "Bronze Ceramic (£480 - Entry Protection)",
        "Ultimate Valet (From £170 - The Full Reset)",
        "Executive Valet (From £110 - Interior Focus)",
        "Premium Valet (From £85 - Exterior Boost)",
        "Standard Valet (From £60 - Essential Clean)",
        "General Inquiry / Custom Quote"
    )

    fun sendViaWhatsApp() {
        if (clientName.isBlank() || clientPhone.isBlank()) {
            Toast.makeText(context, "Please enter your name and phone number", Toast.LENGTH_SHORT).show()
            return
        }

        val message = """
            *Detailing NI Booking Inquiry*
            Name: $clientName
            Phone: $clientPhone
            Vehicle: $vehicleModel ${if (vehicleReg.isNotBlank()) "($vehicleReg)" else ""}
            Package: $selectedPackageName
            Message: ${if (customerMessage.isNotBlank()) customerMessage else "Hi Leslie, I'd like a quote / to book my vehicle in."}
        """.trimIndent()

        try {
            val encodedMessage = URLEncoder.encode(message, "UTF-8")
            val url = "https://wa.me/$BUSINESS_PHONE_INTL?text=$encodedMessage"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Unable to open WhatsApp", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendViaEmail() {
        if (clientName.isBlank() || clientPhone.isBlank()) {
            Toast.makeText(context, "Please enter your name and phone number", Toast.LENGTH_SHORT).show()
            return
        }

        val subject = "Detailing NI Inquiry - $clientName - $vehicleModel"
        val body = """
            Name: $clientName
            Phone: $clientPhone
            Vehicle: $vehicleModel
            Registration: $vehicleReg
            Selected Package: $selectedPackageName
            
            Customer Note:
            ${if (customerMessage.isNotBlank()) customerMessage else "Hi Leslie, please confirm availability and exact pricing for my vehicle."}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$BUSINESS_EMAIL")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            context.startActivity(Intent.createChooser(intent, "Send Email Inquiry"))
        } catch (_: Exception) {
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            DetailingHeader(
                title = "Contact to Book",
                subtitle = "Call, WhatsApp or send your vehicle details directly to Leslie"
            )
        }

        // Direct One-Tap Contact Actions
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Call Leslie
                QuickContactActionCard(
                    title = "Call Leslie Directly",
                    subtitle = "07721 326149 • 20+ yrs experience",
                    icon = Icons.Default.Call,
                    accentColor = CyanAccent,
                    buttonText = "Tap to Call",
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$BUSINESS_PHONE"))
                            context.startActivity(intent)
                        } catch (_: Exception) {}
                    },
                    testTag = "contact_call_button"
                )

                // WhatsApp Chat
                QuickContactActionCard(
                    title = "WhatsApp Chat",
                    subtitle = "Instant messaging & send photos of your car",
                    icon = Icons.Default.Send,
                    accentColor = GreenSuccess,
                    buttonText = "Open WhatsApp",
                    onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$BUSINESS_PHONE_INTL"))
                            context.startActivity(intent)
                        } catch (_: Exception) {}
                    },
                    testTag = "contact_whatsapp_button"
                )
            }
        }

        // Quote & Inquiry Form (Simple, no calendar)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(CyanAccent)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "GET A PERSONALIZED QUOTE",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanAccent,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = "Tell Leslie about your car. He will review paint condition, confirm any vehicle size surcharge, and arrange a date with you.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    // Name
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("Your Name *") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = CyanAccent) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = DarkBg,
                            unfocusedContainerColor = DarkBg,
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_name_input")
                    )

                    // Phone
                    OutlinedTextField(
                        value = clientPhone,
                        onValueChange = { clientPhone = it },
                        label = { Text("Phone / WhatsApp Number *") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = CyanAccent) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = DarkBg,
                            unfocusedContainerColor = DarkBg,
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_phone_input")
                    )

                    // Vehicle Model
                    OutlinedTextField(
                        value = vehicleModel,
                        onValueChange = { vehicleModel = it },
                        label = { Text("Vehicle Make & Model") },
                        placeholder = { Text("e.g. BMW M3, Range Rover, Porsche 911") },
                        leadingIcon = { Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = CyanAccent) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = DarkBg,
                            unfocusedContainerColor = DarkBg,
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_vehicle_input")
                    )

                    // Package Dropdown
                    ExposedDropdownMenuBox(
                        expanded = packageDropdownExpanded,
                        onExpandedChange = { packageDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedPackageName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Package of Interest") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = packageDropdownExpanded) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = DarkBg,
                                unfocusedContainerColor = DarkBg,
                                focusedBorderColor = CyanAccent,
                                unfocusedBorderColor = DarkCardBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .testTag("contact_package_dropdown")
                        )

                        ExposedDropdownMenu(
                            expanded = packageDropdownExpanded,
                            onDismissRequest = { packageDropdownExpanded = false },
                            modifier = Modifier.background(DarkSurfaceElevated)
                        ) {
                            packageOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, color = TextPrimary, fontSize = 13.sp) },
                                    onClick = {
                                        selectedPackageName = option
                                        packageDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Message / Note
                    OutlinedTextField(
                        value = customerMessage,
                        onValueChange = { customerMessage = it },
                        label = { Text("Any specific paint issues or preferred days?") },
                        placeholder = { Text("e.g. Swirl marks on black paint, ceramic top-up needed") },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = DarkBg,
                            unfocusedContainerColor = DarkBg,
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = DarkCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_message_input")
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Send buttons
                    Button(
                        onClick = { sendViaWhatsApp() },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenSuccess, contentColor = Color.Black),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_whatsapp_quote_button")
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send Quote Request via WhatsApp", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { sendViaEmail() },
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("submit_email_quote_button")
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send via Email ($BUSINESS_EMAIL)", fontSize = 12.sp)
                    }
                }
            }
        }

        // Studio Location Card
        item {
            Spacer(modifier = Modifier.height(16.dp))
            StudioLocationCard(
                onOpenMap = {
                    try {
                        val geoUri = Uri.parse("geo:0,0?q=129+Quarry+Heights,+Newtownards,+BT23+7SZ")
                        val intent = Intent(Intent.ACTION_VIEW, geoUri)
                        context.startActivity(intent)
                    } catch (_: Exception) {}
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun QuickContactActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    buttonText: String,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = accentColor,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = buttonText,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun StudioLocationCard(
    onOpenMap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Studio Facility",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = onOpenMap,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CyanAccent),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Directions", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = BUSINESS_ADDRESS,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Secure, insured, temperature-controlled detailing studio. Drop-off by appointment.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = GoldHero, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Mon - Sat: 8:00 - 18:00", style = MaterialTheme.typography.labelSmall, color = TextSecondary, fontSize = 11.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Verified, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Angelwax Certified", style = MaterialTheme.typography.labelSmall, color = CyanAccent, fontSize = 11.sp)
                }
            }
        }
    }
}
