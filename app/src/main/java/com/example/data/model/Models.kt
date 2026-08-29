package com.example.data.model

enum class ServiceCategory(val displayName: String) {
    CERAMIC_COATING("Ceramic Coating"),
    VALETING("Valeting & Maintenance")
}

data class ServicePackage(
    val id: String,
    val name: String,
    val category: ServiceCategory,
    val subtitle: String,
    val price: Double,
    val warrantyOrTag: String,
    val targets: List<String>,
    val highlights: List<String>,
    val isHero: Boolean = false,
    val isPopular: Boolean = false
)

object DetailingPackages {
    val CERAMIC_PACKAGES = listOf(
        ServicePackage(
            id = "ceramic_bronze",
            name = "Bronze Ceramic",
            category = ServiceCategory.CERAMIC_COATING,
            subtitle = "Entry Protection",
            price = 480.0,
            warrantyOrTag = "2-Year Ceramic",
            targets = listOf("Paint", "Glass"),
            highlights = listOf(
                "Deep chemical cleanse & fallout removal",
                "Gloss-enhancing machine polish",
                "2-year Angelwax ceramic coating"
            )
        ),
        ServicePackage(
            id = "ceramic_silver",
            name = "Silver Ceramic",
            category = ServiceCategory.CERAMIC_COATING,
            subtitle = "Full Correction",
            price = 625.0,
            warrantyOrTag = "5-Year Ceramic",
            targets = listOf("Paint", "Glass", "Wheels"),
            highlights = listOf(
                "2-stage machine paint correction",
                "Full swirl & light scratch removal",
                "5-year Angelwax professional coating"
            ),
            isPopular = true
        ),
        ServicePackage(
            id = "ceramic_gold",
            name = "Gold Graphene",
            category = ServiceCategory.CERAMIC_COATING,
            subtitle = "Our Hero Package",
            price = 950.0,
            warrantyOrTag = "5-Year NEBULA Graphene",
            targets = listOf("Paint", "Glass", "Wheels"),
            highlights = listOf(
                "Multi-stage correction (all safe defects)",
                "Angelwax NEBULA Graphene coating",
                "Maximum chemical resistance & slickness"
            ),
            isHero = true
        )
    )

    val VALET_PACKAGES = listOf(
        ServicePackage(
            id = "valet_standard",
            name = "Standard Valet",
            category = ServiceCategory.VALETING,
            subtitle = "Essential Clean",
            price = 60.0,
            warrantyOrTag = "Safe Multi-Stage",
            targets = listOf("Exterior", "Interior"),
            highlights = listOf(
                "Essential clean - safe multi-stage wash, full interior vacuum & wipe down."
            )
        ),
        ServicePackage(
            id = "valet_premium",
            name = "Premium Valet",
            category = ServiceCategory.VALETING,
            subtitle = "Exterior Boost",
            price = 85.0,
            warrantyOrTag = "Ceramic Top-Up",
            targets = listOf("Decon", "Gloss"),
            highlights = listOf(
                "Exterior boost - Standard Valet plus tar removal & ceramic top-up spray for extra gloss & beading."
            )
        ),
        ServicePackage(
            id = "valet_executive",
            name = "Executive Valet",
            category = ServiceCategory.VALETING,
            subtitle = "Interior Focus",
            price = 110.0,
            warrantyOrTag = "Deep Steam Clean",
            targets = listOf("Deep Clean", "Leather"),
            highlights = listOf(
                "Interior focus - Standard Valet plus deep steam clean & conditioning for fabric, leather & plastics."
            )
        ),
        ServicePackage(
            id = "valet_ultimate",
            name = "Ultimate Valet",
            category = ServiceCategory.VALETING,
            subtitle = "The Full Reset",
            price = 170.0,
            warrantyOrTag = "Showroom Reset",
            targets = listOf("Full Reset", "Restoration"),
            highlights = listOf(
                "The full reset - complete exterior decontamination & full interior restoration."
            )
        )
    )

    val ALL_PACKAGES = CERAMIC_PACKAGES + VALET_PACKAGES
    const val SUV_SURCHARGE = 100.0
}

