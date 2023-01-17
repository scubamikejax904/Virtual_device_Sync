/**
 *  Virtual Device Sync Manager
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
    name: "Virtual Device Sync Manager",
    namespace: "ahmedl-HubbaHubba",
    author: "Ahmed L",
    description: "Updates Virtual Device to same state as Physical Device",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: "",
    singleInstance: true)

//https://docs2.hubitat.com/developer/driver/capability-list
//https://docs2.hubitat.com/developer/app/building-a-simple-app
//https://docs2.hubitat.com/developer/app/overview

preferences {
  page(name: "mainPage", title: "Virtual Devices Sync Mgr", install: true, uninstall: true) {
    section {
      app(name: "childApps", appName: "Virtual Device Sync", namespace: "ahmedl-HubbaHubba", title: "Select a pair to sync", multiple: true)
      paragraph "Virtual Device Sync Manager v${version()}"
      input name: "debugOutput", type: "bool", title: "Enable debug logging?", defaultValue: true
    }
  }
}

def installed() {
  log.info "installed(): Installing Virtual Device Sync Manager Parent App"
}

def updated() {
  log.info "updated(): Updating Virtual Device Sync Manager App"
}

def uninstalled() {
  log.info "uninstall(): Uninstalling Virtual Device Sync Manager App"
}
