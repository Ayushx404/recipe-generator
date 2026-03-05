package com.ayush.recipegenerator.data.static

val vegetables =
    listOf(
        "Potato (Aloo)",
        "Onion (Pyaz)",
        "Tomato (Tamatar)",
        "Brinjal/Eggplant (Baingan)",
        "Bottle Gourd (Lauki)",
        "Okra (Bhindi)",
        "Bitter Gourd (Karela)",
        "Ridge Gourd (Torai)",
        "French Beans (Beans/French Beans)",
        "Spinach (Palak)",
        "Fenugreek Leaves (Methi)",
        "Coriander Leaves (Dhaniya)",
        "Cauliflower (Gobi)",
        "Green Chillies (Mirchi)",
        "Drumstick (Sahjan)",
        "Pumpkin (Kaddu)",
        "Cluster Beans (Guar Phali)",
        "Snake Gourd (Parwal)",
        "Indian Bean (Lobia)",
        "Green Peas (Matar)",
        "Bottle Gourd Flower (Lauki Phool)",
        "Mushroom (Khumb)",
        "Asparagus (Shevaga)",
        "Beetroot (Chukandar)",
        "Broccoli (Brokoli)",
        "Carrots (Gajar)",
        "Cucumber (Kheera)",
        "Drumstick Leaves (Sahjan ke Patte)",
        "Snow Peas (Snap Peas)",
    )

val spices =
    listOf(
        "Red Chili Powder (Lal Mirchi Powder)",
        "Cumin Seeds (Jeera)",
        "Coriander Powder (Dhania Powder)",
        "Turmeric Powder (Haldi)",
        "Black Mustard Seeds (Rai)",
        "Fennel Seeds (Saunf)",
        "Cardamom (Elaichi) - Green and Black varieties",
        "Cinnamon (Dalchini)",
        "Cloves (Lavang)",
        "Garam Masala (Garam Masala Powder)",
        "Fenugreek Seeds (Methi Dana)",
        "Black Peppercorns (Kali Mirch)",
        "Bay Leaves (Tej Patta)",
        "Caraway Seeds (Shahi Jeera)",
        "Star Anise (Chakra Phool)",
        "Nigella Seeds (Kalonji)",
        "Mace (Javitri)",
        "Nutmeg (Jaiphal)",
        "Ajwain (Carom Seeds)",
        "Deggi Mirch (Long Red Chillies)",
    )

val cookingOils =
    listOf(
        "Mustard Oil (Sarson ka Tel)",
        "Sunflower Oil (Surajmukhi ka Tel)",
        "Groundnut Oil (Mungfali ka Tel)",
        "Coconut Oil (Nariyal ka Tel)",
        "Palm Oil (Palm Tel)",
        "Soybean Oil (Soyabean ka Tel)",
        "Rice Bran Oil (Chawal ki Chot)",
        "Ghee (Clarified Butter)",
        "Cottonseed Oil (Kapas ke Beej ka Tel)",
        "Refined Sunflower Oil (Surajmukhi ka Tel)",
        "Olive Oil (Zaitoon ka Tel)",
        "Avocado Oil (Avocardo ka Tel)",
        "Sesame Oil (Til ka Tel)",
        "Canola Oil (Kanoda ka Tel)",
        "Castor Oil (Erand ka Tel)",
        "Almond Oil (Badam ka Tel)",
        "Flaxseed Oil (Alsi ka Tel)",
        "Walnut Oil (Akhrot ka Tel)",
        "Grapeseed Oil (Angoor ke Beej ka Tel)",
        "Peanut Oil (Mungfali ka Tel)",
    )

val dairyProducts =
    listOf(
        "Ghee (Clarified Butter)",
        "Curd (Dahi)",
        "Paneer (Indian Cottage Cheese)",
        "Butter (Makhan)",
        "Yogurt (Dahi)",
        "Milk (Doodh)",
        "Fresh Cream (Malai)",
        "Khoya (Mawa)",
        "Buttermilk (Chaas)",
        "Cheese (Pनीर - Pnir)",
    )


fun extractMainName(fullName: String): String {
    val regex = Regex("^[^(]+")
    val matchResult = regex.find(fullName)
    return matchResult?.value?.trim() ?: fullName
}