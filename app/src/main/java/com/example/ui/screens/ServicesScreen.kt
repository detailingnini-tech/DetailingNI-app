package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DetailingPackages
import com.example.data.model.ServiceCategory
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
import java.util.Locale

@Composable
fun ServicesScreen(
    onSelectPackageForContact: (ServicePackage) -> Unit,
    onGeneralContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategoryFilter by remember { mutableStateOf<ServiceCategory?>(null) }

    val displayedPackages = remember(selectedCategoryFilter) {
        if (selectedCategoryFilter == null) {
            DetailingPackages.ALL_PACKAGES
        } else {
            DetailingPackages.ALL_PACKAGES.filter { it.category == selectedCategoryFilter }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        DetailingHeader(
            title = "Services & Pricing",
            subtitle = "Angelwax certified ceramic coatings & precision valeting"
        )

        // Filter chips (Ceramic, Valeting, All)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedCategoryFilter == null,
                onClick = { selectedCategoryFilter = null },
                label = { Text("All (${DetailingPackages.ALL_PACKAGES.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyanAccent,
                    selectedLabelColor = Color.Black,
                    containerColor = DarkSurface,
                    labelColor = TextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedCategoryFilter == null,
                    borderColor = DarkCardBorder,
                    selectedBorderColor = CyanAccent
                )
            )

            FilterChip(
                selected = selectedCategoryFilter == ServiceCategory.CERAMIC_COATING,
                onClick = { selectedCategoryFilter = ServiceCategory.CERAMIC_COATING },
                label = { Text("Ceramic Coatings (3)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyanAccent,
                    selectedLabelColor = Color.Black,
                    containerColor = DarkSurface,
                    labelColor = TextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedCategoryFilter == ServiceCategory.CERAMIC_COATING,
                    borderColor = DarkCardBorder,
                    selectedBorderColor = CyanAccent
                )
            )

            FilterChip(
                selected = selectedCategoryFilter == ServiceCategory.VALETING,
                onClick = { selectedCategoryFilter = ServiceCategory.VALETING },
                label = { Text("Valeting (4)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CyanAccent,
                    selectedLabelColor = Color.Black,
                    containerColor = DarkSurface,
                    labelColor = TextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selectedCategoryFilter == ServiceCategory.VALETING,
                    borderColor = DarkCardBorder,
                    selectedBorderColor = CyanAccent
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Vehicle Size Notice Box
            item {
                VehicleSizeNoticeCard()
            }

            // Package Cards
            items(displayedPackages, key = { it.id }) { servicePackage ->
                CustomerPackageCard(
                    servicePackage = servicePackage,
                    onInquire = { onSelectPackageForContact(servicePackage) }
                )
            }

            // Bottom CTA Card
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = DarkSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyanAccent.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Unsure Which Package Fits Best?",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Contact Leslie directly for a free consultation or paint assessment.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = onGeneralContact,
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("services_general_contact_cta")
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Contact to Book / Get Advice", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VehicleSizeNoticeCard(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = DarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldHero.copy(alpha = 0.35f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = GoldHero,
                modifier = Modifier
                    .size(18.dp)
                    .padding(top = 1.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Pricing Transparency Note",
                    style = MaterialTheme.typography.titleSmall,
                    color = GoldHero,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Prices shown are starting rates. Final price may vary based on vehicle size, condition, and paint depth. A £100 surcharge applies to larger vehicles (SUVs, 4x4s, and vans) on signature packages.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
fun CustomerPackageCard(
    servicePackage: ServicePackage,
    onInquire: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCeramic = servicePackage.category == ServiceCategory.CERAMIC_COATING
    val monthlyPayment = servicePackage.price / 3.0

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("package_card_${servicePackage.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = androidx.compose.foundation.BorderStroke(
            width = if (servicePackage.isHero) 1.5.dp else 1.dp,
            color = when {
                servicePackage.isHero -> GoldHero.copy(alpha = 0.7f)
                servicePackage.isPopular -> CyanAccent.copy(alpha = 0.6f)
                else -> DarkCardBorder
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Badges row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isCeramic) CyanDark.copy(alpha = 0.4f) else DarkSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(
                        0.5.dp,
                        if (isCeramic) CyanAccent.copy(alpha = 0.5f) else DarkCardBorder
                    )
                ) {
                    Text(
                        text = servicePackage.warrantyOrTag,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isCeramic) CyanAccent else TextSecondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                if (servicePackage.isHero) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = GoldHero.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, GoldHero)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = GoldHero, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "HERO DETAIL",
                                style = MaterialTheme.typography.labelSmall,
                                color = GoldHero,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 10.sp
                            )
                        }
                    }
                } else if (servicePackage.isPopular) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CyanAccent.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(0.5.dp, CyanAccent)
                    ) {
                        Text(
                            text = "MOST POPULAR",
                            style = MaterialTheme.typography.labelSmall,
                            color = CyanAccent,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title & Pricing Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = servicePackage.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = servicePackage.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isCeramic) CyanAccent else TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (isCeramic) "£${servicePackage.price.toInt()}" else "From £${servicePackage.price.toInt()}",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (servicePackage.isHero) GoldHero else TextPrimary,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = if (isCeramic) "inc. VAT" else "based on size",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = DarkCardBorder)
            Spacer(modifier = Modifier.height(10.dp))

            // Highlights List
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                servicePackage.highlights.forEach { highlight ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (isCeramic) CyanAccent.copy(alpha = 0.2f) else DarkSurfaceElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = if (isCeramic) CyanAccent else GreenSuccess,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = highlight,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Payment Assist note for ceramic
            if (isCeramic) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = GoldHero.copy(alpha = 0.08f),
                    border = androidx.compose.foundation.BorderStroke(0.5.dp, GoldHero.copy(alpha = 0.25f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CreditCard,
                            contentDescription = null,
                            tint = GoldHero,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Finance: 3x £${String.format(Locale.UK, "%.2f", monthlyPayment)}/mo @ 0% via Payment Assist",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldHero,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // CTA on this package
            Button(
                onClick = onInquire,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (servicePackage.isHero) GoldHero else CyanAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .testTag("inquire_package_${servicePackage.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Contact to Book ${servicePackage.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
