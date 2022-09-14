package com.sushant.sampledemomvvmapicall.service.provider

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.constant.Constant
import com.sushant.sampledemomvvmapicall.constant.dayList
import com.sushant.sampledemomvvmapicall.model.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.SingleEmitter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Service Provider is anything that will accept UI input.
 * and return result to show on UI eg. API Service , Local Database etc
 * We can create extra layer here as per requirement.
 */
object ServiceProvider : IServiceProvider {

    /**
     * You can change service provider inside this method as per requirements.
     */
    override fun getFeeds(page: Int): Observable<FeedResponse> {
        //subscribeOn(Schedulers.io()) tell system to load data on background thread.
        return checkWorkingHoursAndGetFeedResponse()
    }

    private fun checkWorkingHoursAndGetFeedResponse(): Observable<FeedResponse> {
        return Observable.create { emitter ->
            if (isWorkingHours(emitter)) {
                emitter.onNext(getFeedResponse())
                emitter.onComplete()
            }
        }
    }

    private fun getFeedResponse(): FeedResponse {
        val string = App.appContext.assets.open(Constant.PET_LIST_JSON_FILE).bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(string, FeedResponse::class.java)
    }

    private fun isWorkingHours(emitter: ObservableEmitter<FeedResponse>): Boolean {
        var result = false
        val string = App.appContext.assets.open(Constant.CONFIG_JSON_FILE).bufferedReader()
            .use { it.readText() }
        val config = Gson().fromJson(string, ConfigResponse::class.java)
        config?.let {
            val list = config.settings?.workHours?.split(" ")
            Log.d(ServiceProvider.javaClass.name, " List - " + list.toString())
            if (!list.isNullOrEmpty()) {
                val day = list[Constant.DAY_INDEX]
                val from = list[Constant.FROM_TIME_INDEX]
                val to = list[Constant.TO_TIME_INDEX]
                result = isTimeLies(from, to, day, emitter)
            }
        }
        return result
    }

    private fun getDayNameFromInitials(initials: String): Pair<String, String> {
        val values = initials.split("-")
        return values[0] to values[1]
    }

    private fun getPerfectTime(time: String): String {
        val requiredSize = 3
        val list = time.split(":").toMutableList()
        if (list.size < requiredSize) {
            var size = list.size
            while (size <= requiredSize) {
                list.add("00")
                size++
            }
        }
        return list.joinToString(separator = ":") { it }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isTimeLies(
        from: String,
        to: String,
        day: String,
        emitter: ObservableEmitter<FeedResponse>
    ): Boolean {
        var isTimeLiesBetween = false
        var error: ConfigError? = null
        try {
            val (fromDay, toDay) = getDayNameFromInitials(day)// m , f
            val fromDayOfWeek = dayList[fromDay.toUpperCase(Locale.ROOT)] //2
            val toDayOfWeek = dayList[toDay.toUpperCase(Locale.ROOT)] //6

            val calendarCurrent = Calendar.getInstance()
            calendarCurrent.time = Date()
            val currentDayOfWeek = calendarCurrent.get(Calendar.DAY_OF_WEEK)

            //Check in between week day
            if (fromDayOfWeek != null && toDayOfWeek != null && (currentDayOfWeek in (fromDayOfWeek + 1) until toDayOfWeek)) {
                val fromTime: Date? = SimpleDateFormat("HH:mm:ss").parse(getPerfectTime(from))
                val calendarFrom = Calendar.getInstance()
                fromTime?.let {
                    calendarFrom.time = fromTime
                }
                calendarFrom.set(Calendar.DAY_OF_MONTH, calendarCurrent.get(Calendar.DAY_OF_MONTH));
                calendarFrom.set(Calendar.MONTH, calendarCurrent.get(Calendar.MONTH));
                calendarFrom.set(Calendar.YEAR, calendarCurrent.get(Calendar.YEAR));

                val toTime: Date? = SimpleDateFormat("HH:mm:ss").parse(getPerfectTime(to))
                val calendarTo = Calendar.getInstance()
                toTime?.let {
                    calendarTo.time = toTime
                }
                calendarTo.set(Calendar.DAY_OF_MONTH, calendarCurrent.get(Calendar.DAY_OF_MONTH));
                calendarTo.set(Calendar.MONTH, calendarCurrent.get(Calendar.MONTH));
                calendarTo.set(Calendar.YEAR, calendarCurrent.get(Calendar.YEAR));

                //Check between time
                if (calendarCurrent.time.after(calendarFrom.time)
                    && calendarCurrent.time.before(calendarTo.time)
                ) {
                    isTimeLiesBetween = true
                } else {
                    error = WorkHorsNotMatch
                }
            } else {
                error = WorkHorsNotMatch
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            error = ConfigFormatError(
                "Expected Format = M-F 19:00:00 - 18:00:00" +
                        " \nCurrent Format =  From Time: $from, To Time: $to, Day : $day " +
                        "Should be in proper format"
            )

        } finally {
            if (error != null) {
                emitter.onError(error)
            }
        }
        return isTimeLiesBetween
    }

}