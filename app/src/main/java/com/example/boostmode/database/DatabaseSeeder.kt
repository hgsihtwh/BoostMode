package com.example.boostmode.database

import com.example.boostmode.database.entity.CircuitEntity
import com.example.boostmode.database.entity.DriverEntity
import com.example.boostmode.database.entity.RaceEntity

object DatabaseSeeder {

    fun getRaces(): List<RaceEntity> = listOf(
        RaceEntity("australia", "Australia", "Melbourne", "01", "06-08 mar", "previous"),
        RaceEntity("china", "China", "Shanghai", "02", "13-15 mar", "previous"),
        RaceEntity("japan", "Japan", "Suzuka", "03", "27-29 mar", "previous"),
        RaceEntity("miami", "Miami", "Miami", "04", "01-03 may", "current"),
        RaceEntity("canada", "Canada", "Montreal", "05", "22-24 may", "upcoming"),
        RaceEntity("monaco", "Monaco", "Monte Carlo", "06", "05-07 jun", "upcoming"),
        RaceEntity("barcelona", "Spain", "Barcelona", "07", "19-21 jun", "upcoming"),
        RaceEntity("madrid", "Spain", "Madrid", "08", "03-05 jul", "upcoming"),
        RaceEntity("britain", "Great Britain", "Silverstone", "09", "17-19 jul", "upcoming"),
        RaceEntity("belgium", "Belgium", "Spa", "10", "31 jul-02 aug", "upcoming"),
        RaceEntity("hungary", "Hungary", "Budapest", "11", "07-09 aug", "upcoming"),
        RaceEntity("netherlands", "Netherlands", "Zandvoort", "12", "21-23 aug", "upcoming"),
        RaceEntity("italy", "Italy", "Monza", "13", "04-06 sep", "upcoming"),
        RaceEntity("azerbaijan", "Azerbaijan", "Baku", "14", "19 sep", "upcoming"),
        RaceEntity("singapore", "Singapore", "Singapore", "15", "02-04 oct", "upcoming"),
        RaceEntity("usa", "USA", "Austin", "16", "23-25 oct", "upcoming"),
        RaceEntity("mexico", "Mexico", "Mexico City", "17", "30 oct-01 nov", "upcoming"),
        RaceEntity("brazil", "Brazil", "São Paulo", "18", "13-15 nov", "upcoming"),
        RaceEntity("lasvegas", "Las Vegas", "Las Vegas", "19", "21-22 nov", "upcoming"),
        RaceEntity("qatar", "Qatar", "Lusail", "20", "28-30 nov", "upcoming"),
        RaceEntity("abudhabi", "Abu Dhabi", "Yas Marina", "21", "04-06 dec", "upcoming")
    )

    fun getDrivers(): List<DriverEntity> = listOf(
        // McLaren
        DriverEntity("norris", "Lando", "Norris", "McLaren", "4"),
        DriverEntity("piastri", "Oscar", "Piastri", "McLaren", "81"),
        // Mercedes
        DriverEntity("russell", "George", "Russell", "Mercedes", "63"),
        DriverEntity("antonelli", "Kimi", "Antonelli", "Mercedes", "12"),
        // Red Bull
        DriverEntity("verstappen", "Max", "Verstappen", "Red Bull", "1"),
        DriverEntity("hadjar", "Isack", "Hadjar", "Red Bull", "6"),
        // Ferrari
        DriverEntity("leclerc", "Charles", "Leclerc", "Ferrari", "16"),
        DriverEntity("hamilton", "Lewis", "Hamilton", "Ferrari", "44"),
        // Williams
        DriverEntity("sainz", "Carlos", "Sainz", "Williams", "55"),
        DriverEntity("albon", "Alexander", "Albon", "Williams", "23"),
        // Racing Bulls
        DriverEntity("lawson", "Liam", "Lawson", "Racing Bulls", "30"),
        DriverEntity("lindblad", "Arvid", "Lindblad", "Racing Bulls", "62"),
        // Aston Martin
        DriverEntity("alonso", "Fernando", "Alonso", "Aston Martin", "14"),
        DriverEntity("stroll", "Lance", "Stroll", "Aston Martin", "18"),
        // Haas
        DriverEntity("ocon", "Esteban", "Ocon", "Haas", "31"),
        DriverEntity("bearman", "Oliver", "Bearman", "Haas", "87"),
        // Audi
        DriverEntity("hulkenberg", "Nico", "Hülkenberg", "Audi", "27"),
        DriverEntity("bortoleto", "Gabriel", "Bortoleto", "Audi", "5"),
        // Alpine
        DriverEntity("gasly", "Pierre", "Gasly", "Alpine", "10"),
        DriverEntity("colapinto", "Franco", "Colapinto", "Alpine", "43"),
        // Cadillac
        DriverEntity("perez", "Sergio", "Pérez", "Cadillac", "11"),
        DriverEntity("bottas", "Valtteri", "Bottas", "Cadillac", "77")
    )

    fun getCircuits(): List<CircuitEntity> = listOf(
        CircuitEntity(
            raceId = "australia",
            location = "Melbourne, Australia",
            opened = "1953",
            firstF1Gp = "1996",
            turns = "16",
            length = "5.278 km",
            laps = "58",
            about = "The Albert Park Circuit is a street circuit in Melbourne, Australia. Located around a lake in Albert Park, it has hosted the Australian Grand Prix since 1996 and traditionally opens the F1 season.",
            lapTimeRecord = "1:20.235",
            driver = "Charles Leclerc",
            car = "Ferrari SF-24",
            year = "2024",
            avgSpeed = "236.1 km/h"
        ),
        CircuitEntity(
            raceId = "china",
            location = "Shanghai, China",
            opened = "2004",
            firstF1Gp = "2004",
            turns = "16",
            length = "5.451 km",
            laps = "56",
            about = "The Shanghai International Circuit is a purpose-built racing venue designed by Hermann Tilke. The circuit features a unique spiral-shaped infield section and long straight.",
            lapTimeRecord = "1:32.238",
            driver = "Michael Schumacher",
            car = "Ferrari F2004",
            year = "2004",
            avgSpeed = "212.8 km/h"
        ),
        CircuitEntity(
            raceId = "japan",
            location = "Suzuka, Japan",
            opened = "1962",
            firstF1Gp = "1987",
            turns = "18",
            length = "5.807 km",
            laps = "53",
            about = "Suzuka Circuit is a figure-eight-shaped racing circuit in Japan. It is renowned for its technical layout featuring fast corners, chicanes, and the famous 130R corner. One of the most beloved circuits on the calendar.",
            lapTimeRecord = "1:30.983",
            driver = "Lewis Hamilton",
            car = "Mercedes W05",
            year = "2024",
            avgSpeed = "230.5 km/h"
        ),
        CircuitEntity(
            raceId = "miami",
            location = "Miami, USA",
            opened = "2022",
            firstF1Gp = "2022",
            turns = "19",
            length = "5.412 km",
            laps = "57",
            about = "The Miami International Autodrome is a street circuit built around the Hard Rock Stadium complex. It debuted in 2022 and quickly became a fan favourite with its vibrant atmosphere.",
            lapTimeRecord = "1:29.708",
            driver = "Max Verstappen",
            car = "Red Bull RB20",
            year = "2024",
            avgSpeed = "219.4 km/h"
        ),
        CircuitEntity(
            raceId = "monaco",
            location = "Monte Carlo, Monaco",
            opened = "1929",
            firstF1Gp = "1950",
            turns = "19",
            length = "3.337 km",
            laps = "78",
            about = "The Circuit de Monaco is the most glamorous race on the F1 calendar, run through the narrow streets of Monte Carlo. It is the slowest and most prestigious Grand Prix, known for its tight barriers and zero margin for error.",
            lapTimeRecord = "1:10.166",
            driver = "Lewis Hamilton",
            car = "Mercedes W12",
            year = "2021",
            avgSpeed = "157.2 km/h"
        ),
        CircuitEntity(
            raceId = "italy",
            location = "Monza, Italy",
            opened = "1922",
            firstF1Gp = "1950",
            turns = "11",
            length = "5.793 km",
            laps = "53",
            about = "The Autodromo Nazionale Monza, known as the Temple of Speed, is the oldest purpose-built racing circuit still in use. Located in a royal park north of Milan, it features long straights and high-speed corners.",
            lapTimeRecord = "1:21.046",
            driver = "Rubens Barrichello",
            car = "Ferrari F2004",
            year = "2004",
            avgSpeed = "247.6 km/h"
        ),
        CircuitEntity(
            raceId = "netherlands",
            location = "Zandvoort, Netherlands",
            opened = "1948",
            firstF1Gp = "1952",
            turns = "14",
            length = "4.259 km",
            laps = "72",
            about = "Circuit Zandvoort is nestled among the sand dunes of the Dutch coast. Returned to the F1 calendar in 2021, it features iconic banked corners and passionate orange-clad Dutch fans.",
            lapTimeRecord = "1:11.097",
            driver = "Max Verstappen",
            car = "Red Bull RB19",
            year = "2023",
            avgSpeed = "196.8 km/h"
        ),
        CircuitEntity(
            raceId = "britain",
            location = "Silverstone, United Kingdom",
            opened = "1948",
            firstF1Gp = "1950",
            turns = "18",
            length = "5.891 km",
            laps = "52",
            about = "Silverstone Circuit hosted the very first Formula 1 World Championship race in 1950. Known for its fast and flowing layout, Copse and Maggotts-Becketts sections are among the most challenging in F1.",
            lapTimeRecord = "1:27.097",
            driver = "Max Verstappen",
            car = "Red Bull RB19",
            year = "2023",
            avgSpeed = "240.5 km/h"
        ),
        CircuitEntity(
            raceId = "belgium",
            location = "Spa-Francorchamps, Belgium",
            opened = "1921",
            firstF1Gp = "1950",
            turns = "19",
            length = "7.004 km",
            laps = "44",
            about = "Circuit de Spa-Francorchamps is set in the Ardennes forest and is widely regarded as the greatest racing circuit in the world. The iconic Eau Rouge-Raidillon complex is one of the most thrilling sequences in motorsport.",
            lapTimeRecord = "1:41.252",
            driver = "Valtteri Bottas",
            car = "Mercedes W11",
            year = "2020",
            avgSpeed = "234.3 km/h"
        )
    )
}