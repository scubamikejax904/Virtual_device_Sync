/**
 *  Virtual Device Sync
 *
 *  Copyright 2023 Ahmed L
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
//0.0.1MSG Modified to add Motion Sensors and made no required fields By Michael G
//0.0.2MSG Added Locks

public static String version() { return "0.0.2MSG" }

definition(
    name: "Virtual Device Sync",
    parent: "ahmedl-HubbaHubba:Virtual Device Sync Manager",
    namespace: "ahmedl-HubbaHubba",
    author: "Ahmed L",
    description: "Updates Virtual Device to same state as Physical Device",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: ""
)

//https://docs2.hubitat.com/developer/driver/capability-list
//https://docs2.hubitat.com/developer/app/building-a-simple-app
//https://docs2.hubitat.com/developer/app/overview

preferences {
     page(name: "mainPage", title: "Virtual Device Sync", install: true, uninstall: true) {
        section("Contact Sensor") {
            paragraph "Virtual Device Sync  v${version()}"
            input name: "physicalSensor", type: "capability.contactSensor", title: "When open/close is detected on...", submitOnChange: true, required: false, multiple: false
            input name: "virtualSensor", type: "capability.contactSensor", title: "Then update state on this sensor ...", submitOnChange: true, required: false, multiple: false
        }
        section("Motion Sensor") {
            paragraph "Virtual Device Sync  v${version()}"
            input name: "physicalmotionSensor", type: "capability.motionSensor", title: "When motion is detected on...", submitOnChange: true, required: false, multiple: false
            input name: "virtualmotionSensor", type: "capability.motionSensor", title: "Then update state on this sensor ...", submitOnChange: true, required: false, multiple: false
    }
    section("Locks") {
            paragraph "Virtual Device Sync  v${version()}"
            input name: "physicallock", type: "capability.lock", title: "When Lock state is changed on...", submitOnChange: true, required: false, multiple: false
            input name: "virtuallock", type: "capability.lock", title: "Then update state on this Lock ...", submitOnChange: true, required: false, multiple: false
    }
}
}    
def installed() {
    log.info "installed()"
    log.debug "installed()"
    updated()
}

def updated() {
   log.info "updated()"
   unsubscribe()
   app.updateLabel("${physicalSensor?.displayName} --> ${virtualSensor?.displayName} </span>")
    app.updateLabel("${physicalmotionSensor?.displayName} --> ${virtualmotionSensor?.displayName} </span>")
    app.updateLabel("${physicallock?.displayName} --> ${virtuallock?.displayName} </span>")
   subscribe(physicalSensor, "contact", contactHandler)
   subscribe(physicalmotionSensor, "motion", motionHandler)
   subscribe(physicallock, "lock", lockHandler)
}

def uninstalled() {
   log.info "uninstalled()"
   unsubscribe()
}

def contactHandler(evt) {
    log.info "contactHandler() called: ${evt.descriptionText} ${evt.device?.name}"
    log.debug "contactHandler() called: ${evt.name} ${evt.value}"
    if(evt.value == "open"){
        log.info "Sending open() to ${virtualSensor.name}"
        virtualSensor.open()
    }
    else {
        log.info "Sending close() to ${virtualSensor.name}"
        virtualSensor.close()
    }
}
    def motionHandler(evt) {
    log.info "motionHandler() called: ${evt.descriptionText} ${evt.device?.name}"
    log.debug "motionHandler() called: ${evt.name} ${evt.value}"
    if(evt.value == "active"){
        log.info "Sending active() to ${virtualmotionSensor.name}"
        virtualmotionSensor.active()
    }
    else {
        log.info "Sending inactive() to ${virtualmotionSensor.name}"
        virtualmotionSensor.inactive()
    }
    }
        def lockHandler(evt) {
    log.info "lockHandler() called: ${evt.descriptionText} ${evt.device?.name}"
    log.debug "lockHandler() called: ${evt.name} ${evt.value}"
    if(evt.value == "locked"){
        log.info "Sending lock() to ${virtuallock.name}"
        virtuallock.lock()
    }
     else {
        log.info "Sending unlock() to ${virtuallock.name}"
       virtuallock.unlock()
    }
        }
