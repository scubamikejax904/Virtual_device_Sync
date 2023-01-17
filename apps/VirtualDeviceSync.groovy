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

public static String version() { return "0.0.1" }

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
            input name: "physicalContactSensor", type: "capability.contactSensor", title: "When open/close is detected on...", submitOnChange: true, required: true, multiple: false
            input name: "virtualContactSensor", type: "capability.contactSensor", title: "Then update state on this sensor ...", submitOnChange: true, required: true, multiple: false
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
   app.updateLabel("${physicalContactSensor?.displayName} --> ${virtualContactSensor?.displayName} </span>")
   subscribe(physicalContactSensor, "contact", contactHandler)
}

def uninstalled() {
   log.info "uninstalled()"
   unsubscribe()
}

def contactHandler(evt) {
    log.info "contactHandler() called: ${evt.descriptionText} ${evt.device?.name}"
    log.debug "contactHandler() called: ${evt.name} ${evt.value}"
    if(evt.value == "open"){
        log.info "Sending open() to ${virtualContactSensor.name}"
        virtualContactSensor.open()
    }
    else {
        log.info "Sending close() to ${virtualContactSensor.name}"
        virtualContactSensor.close()
    }
}
