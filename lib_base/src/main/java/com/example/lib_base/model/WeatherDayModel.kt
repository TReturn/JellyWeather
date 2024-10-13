package com.example.lib_base.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


/**
 * @CreateDate: 2024/10/11 04:03
 * @Author: 青柠
 * @Description:
 */
@Serializable
data class WeatherDayModel(
    @SerialName("api_status")
    var apiStatus: String = "",
    @SerialName("api_version")
    var apiVersion: String = "",
    @SerialName("lang")
    var lang: String = "",
    @SerialName("location")
    var location: List<Double> = listOf(),
    @SerialName("result")
    var result: Result = Result(),
    @SerialName("server_time")
    var serverTime: Int = 0,
    @SerialName("status")
    var status: String = "",
    @SerialName("timezone")
    var timezone: String = "",
    @SerialName("tzshift")
    var tzshift: Int = 0,
    @SerialName("unit")
    var unit: String = ""
) {
    @Serializable
    data class Result(
        @SerialName("daily")
        var daily: Daily = Daily(),
        @SerialName("primary")
        var primary: Int = 0
    ) {
        @Serializable
        data class Daily(
            @SerialName("air_quality")
            var airQuality: AirQuality = AirQuality(),
            @SerialName("astro")
            var astro: List<Astro> = listOf(),
            @SerialName("cloudrate")
            var cloudrate: List<Cloudrate> = listOf(),
            @SerialName("dswrf")
            var dswrf: List<Dswrf> = listOf(),
            @SerialName("humidity")
            var humidity: List<Humidity> = listOf(),
            @SerialName("life_index")
            var lifeIndex: LifeIndex = LifeIndex(),
            @SerialName("precipitation")
            var precipitation: List<Precipitation> = listOf(),
            @SerialName("precipitation_08h_20h")
            var precipitation08h20h: List<Precipitation08h20h> = listOf(),
            @SerialName("precipitation_20h_32h")
            var precipitation20h32h: List<Precipitation20h32h> = listOf(),
            @SerialName("pressure")
            var pressure: List<Pressure> = listOf(),
            @SerialName("skycon")
            var skycon: List<Skycon> = listOf(),
            @SerialName("skycon_08h_20h")
            var skycon08h20h: List<Skycon08h20h> = listOf(),
            @SerialName("skycon_20h_32h")
            var skycon20h32h: List<Skycon20h32h> = listOf(),
            @SerialName("status")
            var status: String = "",
            @SerialName("temperature")
            var temperature: List<Temperature> = listOf(),
            @SerialName("temperature_08h_20h")
            var temperature08h20h: List<Temperature08h20h> = listOf(),
            @SerialName("temperature_20h_32h")
            var temperature20h32h: List<Temperature20h32h> = listOf(),
            @SerialName("visibility")
            var visibility: List<Visibility> = listOf(),
            @SerialName("wind")
            var wind: List<Wind> = listOf(),
            @SerialName("wind_08h_20h")
            var wind08h20h: List<Wind08h20h> = listOf(),
            @SerialName("wind_20h_32h")
            var wind20h32h: List<Wind20h32h> = listOf()
        ) {
            @Serializable
            data class AirQuality(
                @SerialName("aqi")
                var aqi: List<Aqi> = listOf(),
                @SerialName("pm25")
                var pm25: List<Pm25> = listOf()
            ) {
                @Serializable
                data class Aqi(
                    @SerialName("avg")
                    var avg: Avg = Avg(),
                    @SerialName("date")
                    var date: String = "",
                    @SerialName("max")
                    var max: Max = Max(),
                    @SerialName("min")
                    var min: Min = Min()
                ) {
                    @Serializable
                    data class Avg(
                        @SerialName("chn")
                        var chn: Int = 0,
                        @SerialName("usa")
                        var usa: Int = 0
                    )

                    @Serializable
                    data class Max(
                        @SerialName("chn")
                        var chn: Int = 0,
                        @SerialName("usa")
                        var usa: Int = 0
                    )

                    @Serializable
                    data class Min(
                        @SerialName("chn")
                        var chn: Int = 0,
                        @SerialName("usa")
                        var usa: Int = 0
                    )
                }

                @Serializable
                data class Pm25(
                    @SerialName("avg")
                    var avg: Int = 0,
                    @SerialName("date")
                    var date: String = "",
                    @SerialName("max")
                    var max: Int = 0,
                    @SerialName("min")
                    var min: Int = 0
                )
            }

            @Serializable
            data class Astro(
                @SerialName("date")
                var date: String = "",
                @SerialName("sunrise")
                var sunrise: Sunrise = Sunrise(),
                @SerialName("sunset")
                var sunset: Sunset = Sunset()
            ) {
                @Serializable
                data class Sunrise(
                    @SerialName("time")
                    var time: String = ""
                )

                @Serializable
                data class Sunset(
                    @SerialName("time")
                    var time: String = ""
                )
            }

            @Serializable
            data class Cloudrate(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class Dswrf(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class Humidity(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class LifeIndex(
                @SerialName("carWashing")
                var carWashing: List<CarWashing> = listOf(),
                @SerialName("coldRisk")
                var coldRisk: List<ColdRisk> = listOf(),
                @SerialName("comfort")
                var comfort: List<Comfort> = listOf(),
                @SerialName("dressing")
                var dressing: List<Dressing> = listOf(),
                @SerialName("ultraviolet")
                var ultraviolet: List<Ultraviolet> = listOf()
            ) {
                @Serializable
                data class CarWashing(
                    @SerialName("date")
                    var date: String = "",
                    @SerialName("desc")
                    var desc: String = "",
                    @SerialName("index")
                    var index: String = ""
                )

                @Serializable
                data class ColdRisk(
                    @SerialName("date")
                    var date: String = "",
                    @SerialName("desc")
                    var desc: String = "",
                    @SerialName("index")
                    var index: String = ""
                )

                @Serializable
                data class Comfort(
                    @SerialName("date")
                    var date: String = "",
                    @SerialName("desc")
                    var desc: String = "",
                    @SerialName("index")
                    var index: String = ""
                )

                @Serializable
                data class Dressing(
                    @SerialName("date")
                    var date: String = "",
                    @SerialName("desc")
                    var desc: String = "",
                    @SerialName("index")
                    var index: String = ""
                )

                @Serializable
                data class Ultraviolet(
                    @SerialName("date")
                    var date: String = "",
                    @SerialName("desc")
                    var desc: String = "",
                    @SerialName("index")
                    var index: String = ""
                )
            }

            @Serializable
            data class Precipitation(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0,
                @SerialName("probability")
                var probability: Int = 0
            )

            @Serializable
            data class Precipitation08h20h(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0,
                @SerialName("probability")
                var probability: Int = 0
            )

            @Serializable
            data class Precipitation20h32h(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0,
                @SerialName("probability")
                var probability: Int = 0
            )

            @Serializable
            data class Pressure(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class Skycon(
                @SerialName("date")
                var date: String = "",
                @SerialName("value")
                var value: String = ""
            )

            @Serializable
            data class Skycon08h20h(
                @SerialName("date")
                var date: String = "",
                @SerialName("value")
                var value: String = ""
            )

            @Serializable
            data class Skycon20h32h(
                @SerialName("date")
                var date: String = "",
                @SerialName("value")
                var value: String = ""
            )

            @Serializable
            data class Temperature(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class Temperature08h20h(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class Temperature20h32h(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class Visibility(
                @SerialName("avg")
                var avg: Double = 0.0,
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Double = 0.0,
                @SerialName("min")
                var min: Double = 0.0
            )

            @Serializable
            data class Wind(
                @SerialName("avg")
                var avg: Avg = Avg(),
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Max = Max(),
                @SerialName("min")
                var min: Min = Min()
            ) {
                @Serializable
                data class Avg(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )

                @Serializable
                data class Max(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )

                @Serializable
                data class Min(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )
            }

            @Serializable
            data class Wind08h20h(
                @SerialName("avg")
                var avg: Avg = Avg(),
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Max = Max(),
                @SerialName("min")
                var min: Min = Min()
            ) {
                @Serializable
                data class Avg(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )

                @Serializable
                data class Max(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )

                @Serializable
                data class Min(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )
            }

            @Serializable
            data class Wind20h32h(
                @SerialName("avg")
                var avg: Avg = Avg(),
                @SerialName("date")
                var date: String = "",
                @SerialName("max")
                var max: Max = Max(),
                @SerialName("min")
                var min: Min = Min()
            ) {
                @Serializable
                data class Avg(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )

                @Serializable
                data class Max(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )

                @Serializable
                data class Min(
                    @SerialName("direction")
                    var direction: Double = 0.0,
                    @SerialName("speed")
                    var speed: Double = 0.0
                )
            }
        }
    }
}