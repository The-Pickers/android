package gsc.ThePickers.util

object dummyComment {
    private val completeList = listOf<String>(
        "Earth Guardian! You've reduced carbon by 0.33 kg and trash by 0.51 kg so far 🌍💚",
        "Earth Hero! You've reduced approximately 0.33 kg of carbon and 0.51 kg of waste so far 🌍💚",
        "Amazing work! You helped reduce 0.14 kg of CO₂ and collected 0.4 kg of waste 🥰🌱",
        "Wonderful job, green warrior! You've cut 0.19 kg of carbon and picked up 0.47 kg of waste! 🌎🌱",
        "Fantastic job! You've reduced 0.112 kg of carbon and 0.165 kg of waste.💖🌱",
        "Eco-champion alert! You've reduced 0.15 kg of carbon and 0.78 kg of waste. Keep up the fantastic work! 🏆👏",
        "Eco-warrior in action! You've reduced 0.17 kg of carbon and 0.44 kg of waste! 🥰🌱",
        "Fantastic recycling! You've reduced 0.237 kg of carbon and 0.115 kg of waste. 💚🌏",
        "Bravo, eco-warrior! You've reduced 0.17 kg of carbon and 0.4 kg of waste 💖🌿",
        "Eco-champion! You've reduced 0.17 kg of carbon and 0.38 kg of waste 💚🌏 Every little bit counts!"
    )

    private val shortList = listOf<String>(
        "Great job! 0.33 kg carbon, 0.51 kg trash gone! \uD83C\uDF0D\uD83D\uDC9A",
        "0.33 kg carbon and 0.51 kg waste reduced! Earth thanks you!\uD83C\uDF0D\uD83D\uDC9A",
        "Nice work! 0.14 kg CO₂ and 0.4 kg waste reduced! \uD83C\uDF31",
        "0.19 kg carbon, 0.47 kg waste go green warrior! \uD83C\uDF0E",
        "0.112 kg carbon, 0.165 kg trash Earth loves you! \uD83D\uDC96",
        "0.15 kg carbon, 0.78 kg waste you're a champ! \uD83C\uDFC6",
        "Eco-win! 0.17 kg carbon and 0.44 kg trash gone! \uD83C\uDF31",
        " 0.237 kg carbon, 0.115 kg waste recycling pro! \uD83D\uDC9A",
        " Bravo! 0.17 kg carbon and 0.4 kg waste cut! \uD83D\uDC96\uD83C\uDF3F",
        "Champion move! 0.17 kg carbon, 0.38 kg waste! \uD83C\uDF0F")


    private val commentList = listOf<String>(
        "Your actions are helping whales sing their songs for generations to come! \uD83D\uDC33\uD83C\uDFB6",
        "Your efforts are helping the great forest breathe a little easier \uD83C\uDF33\uD83C\uDF33",
        "You're making waves for sea turtle conservation! \uD83D\uDC22\uD83C\uDF0A",
        "Your love for trees is helping our planet breathe easier \uD83C\uDF33!",
        "A little bird is singing a song of hope thanks to your efforts! \uD83D\uDC26\uD83C\uDFB6",
        "The forest is breathing easier thanks to your efforts! \uD83C\uDF32\uD83D\uDC9A",
        "Your actions help fish swim freely in cleaner waters! \uD83D\uDC1F",
        "Your efforts are planting seeds of hope for a greener tomorrow \uD83C\uDF31",
        "Your efforts are helping flowers bloom brighter every day! \uD83C\uDF38\uD83C\uDF3C",
        "Your actions are helping flowers bloom even brighter! \uD83C\uDF38"
    )
    fun getComplete(idx : Int) = completeList[idx% completeList.size]
    fun getComment(idx : Int) = commentList[idx% commentList.size]
    fun getShort(idx : Int) = shortList[idx% shortList.size]

}