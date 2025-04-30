package gsc.ZupStar.util

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
    fun getComplete(idx : Int) = completeList[idx]
    fun getComment(idx : Int) = commentList[idx]

}