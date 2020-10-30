/**
 *  turn on at exact date and time
 *
 *  Copyright 2017 ELY M.
 *
if you wish to use the APIs with string inputs directly, you will need to understand their expected format. 
SmartThings uses the Java standard format of “yyyy-MM-dd’T’HH:mm:ss.SSSZ”. 
More technical readers may recognize this format as ISO-8601 (Java does not fully conform to this format, but it is very similar). 
Full discussion of this format is beyond the scope of this documentation, but 
a few examples may help: “January 09, 2015 3:50:32 GMT-6 (Central Standard Time)” converts to “2015-01-09T15:50:32.000-0600”, 
and “February 09, 2015 3:50:32:254 GMT-6 (Central Standard Time)” converts to “2015-02-09T15:50:32.254-0600” 
For more information about date formatting, you can review the SimpleDateFormat JavaDoc.

new Date().format("yyyy-MM-dd'T'HH:mm:ss'Z'", TimeZone.getTimeZone("UTC")) 
Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", dateString)


Updated with settings: [starttime:2017-12-10T02:20:00.000-0600, endtime:2017-12-10T02:21:00.000-0600]
Installed with settings: [starttime:2017-12-10T02:20:00.000-0600, endtime:2017-12-10T02:21:00.000-0600]

FUCK YOU Smartthings, you are fucking trash!!!!!!     


// for sweet hubitat!!!!    
toDateTime

Signature
        Date toDateTime(String dateTimeString)

Parameters
        dateTimeString - A date/time string in ISO8601 format. ("yyyy-MM-dd", "yyyyMMdd'T'HHmmssX", "yyyy-MM-dd'T'HH:mm:ssX", "yyyy-MM-dd'T'HH:mm:ss.SSSX")



 *
 */
 
definition(
    name: "turn on and off switches at exact times",
    namespace: "ELY3M",
    author: "ELY3M",
    description: "turn on and off switches at exact times",
    category: "My Apps",
	iconUrl: "",
	iconX2Url: "",
	iconX3Url: ""
)

preferences {
	page(name: "main")
}

def main()  { 
	dynamicPage(name: "main", title: "Send Hub Events", uninstall: true, install: true) {
	
	section("Select Switch") {
			input "switches", "capability.switch", title: "Select Switch", required: true, multiple: true
		}

	def nowdate = new Date(now())
    def nowmonth = nowdate.format('MM')
	def nowday = nowdate.format('dd') 
    def nowyear = nowdate.format('yyyy')
    log.debug "date on pref: ${nowmonth}-${nowday}-${nowyear}"


    section("Start Time") {
      //input name: "startMonth", type: "number", title: "Month", required: false, defaultValue: nowmonth
      //input name: "startDay", type: "number", title: "Day", required: false, defaultValue: nowday
      //input name: "startYear", type: "number", description: "Format(yyyy)", title: "Year", required: false, defaultValue: nowyear
      input name: "startTime", type: "time", title: "Start Time", description: "Time", required: true
    }
    section("End Time") {
      //input name: "endMonth", type: "number", title: "Month", required: false, defaultValue: nowmonth
      //input name: "endDay", type: "number", title: "Day", required: false, defaultValue: nowday
      //input name: "endYear", type: "number", description: "Format(yyyy)", title: "Year", required: false, defaultValue: nowyear
      input name: "endTime", type: "time", title: "End Time", description: "Time", required: true
}


			
}
}


def installed() {
    initialize()
}


def updated() {
    unsubscribe()
    initialize()
}


def initialize() {
	scheduleTurnOnOffTimes()
}


def scheduleTurnOnOffTimes() {

/*
        Date toDateTime(String dateTimeString)

    Parameters
        dateTimeString - A date/time string in ISO8601 format. ("yyyy-MM-dd", "yyyyMMdd'T'HHmmssX", "yyyy-MM-dd'T'HH:mm:ssX", "yyyy-MM-dd'T'HH:mm:ss.SSSX")
*/




	def time_on = "${startTime}"
	time_on = new Date(timeToday(time_on).time)

	def time_off = "${endTime}"
	time_off = new Date(timeToday(time_off).time)
	
	
	log.debug "time_on: $time_on"
	log.debug "time_off: $time_off"

	
	runOnce(time_on, turnOn)
    runOnce(time_off, turnOff)
	
}





def turnOn(evt) {
	switches.on() //turn on 
	log.debug "turned on switches"
}


def turnOff(evt) {
	switches.off() //turn off
	log.debug "turned off switches"
}



//not used right now//
def get_currenttime(){
	def dateTime = new Date()  //get current time
    def mycurr_time = dateTime.format("h:mma E", location.timeZone) //get current time
	return mycurr_time
}


