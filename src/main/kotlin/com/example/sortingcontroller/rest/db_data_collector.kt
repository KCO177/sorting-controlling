package com.example.sortingcontroller.rest
import org.springframework.web.client.RestTemplate

class db_data_collector() {
    // TODO vvtáhnout urlIndex do settings
    val urlIndex = "http://localhost:8080/"

    //get Int, Double values from invoices records regarding mo Id
    fun get_invoice_value_for_mo(mo: String, requiredValue: String): Double {
        var totalValInvoice = 0.0
        // call rest function find all invoices to current MO (MO:INV; 1:N)
        val restTemplate = RestTemplate()
        val url = "${urlIndex}inv/findinvformo/{mo}"
        val response = restTemplate.getForObject(url, List::class.java, mo)

        //map the JSON response to values
        if (response != null && response.isNotEmpty()) {
            for (invRecord in response) {
                if (invRecord is Map<*, *>) {
                    val valInvoice = invRecord[requiredValue]
                    if (valInvoice is Int) {
                        totalValInvoice += valInvoice.toDouble()
                    }
                    if (valInvoice is Double) {
                        totalValInvoice += valInvoice
                    }

                }
            }
        }
        //println("total $requiredValue in invoices for $mo : $totalValInvoice")
        return totalValInvoice
    }

    //get Int, Double values from mission order record regarding mo Id
    fun get_mission_order_value_regarding_mo(mo: String, requiredValue: String): Double {
        var totalValMo = 0.0
        // call rest function find MO regarding moId
        val restTemplate = RestTemplate()
        val url = "$urlIndex/mo/findmo/{mo}"
        val response = restTemplate.getForObject(url, List::class.java, mo)

        //map the JSON response to values
        if (response != null && response.isNotEmpty()) {
            for (moRecord in response) {
                if (moRecord is Map<*, *>) {
                    val valMo = moRecord[requiredValue]//"cost_mission_order"]
                    if (valMo is Int) {
                        totalValMo += valMo.toDouble()
                    }
                    if (valMo is Double) {
                        totalValMo += valMo
                    }
                }
            }
        }
        //println("total costs in mo $mo : $totalValMo")

        return totalValMo
    }

    //get String values from mission order record regarding mo Id
    fun get_part_from_inv(mo: String, partValueKind: String): String {
        val restTemplate = RestTemplate()
        val url = "${urlIndex}inv/findinvformo/{mo}"
        val response = restTemplate.getForObject(url, List::class.java, mo)
        var partInvoice: String = ""
        //map the JSON response to values
        if (response != null && response.isNotEmpty()) {
            for (invRecord in response) {
                if (invRecord is Map<*, *>) {
                    partInvoice = invRecord[partValueKind].toString()

                }
            }
        }
        return partInvoice
    }

    //get String values from mission order record regarding mo Id
    fun get_part_from_mo(mo: String, partValueKind: String): String {
        var partMo: String = ""
        // call rest function find MO regarding moId
        val restTemplate = RestTemplate()
        val url = "$urlIndex/mo/findmo/{mo}"
        val response = restTemplate.getForObject(url, List::class.java, mo)
        //map the JSON response to values
        if (response != null && response.isNotEmpty()) {
            for (moRecord in response) {
                if (moRecord is Map<*, *>) {
                    partMo = moRecord[partValueKind].toString()
                }
            }
        }
       //println("part val in MO $mo : $partMo")

        return partMo
    }

    //get Int, Double values from part records
    fun get_part_value_to_sort(part: String, partValue: String): Double {
        // call rest function findPart partValue in constructor for time to sort or time to manipulation
        val restTemplate = RestTemplate()
        val url = "$urlIndex/part/{part}"
        val response = restTemplate.getForObject(url, List::class.java, part)
        var partTime: Double = 0.0

        //map the JSON response to values
        if (response != null && response.isNotEmpty()) {
            for (partRecord in response) {
                if (partRecord is Map<*, *>) {
                    partTime = partRecord[partValue] as Double
                }
            }
        }
        //println("For part $part , $partValue is $partTime")
        return partTime
    }

    //get String values from part records
    fun get_part_value(part: String, partItemKind:String): String {
        var partItem: String = ""
        // call rest function find part regarding part (name, shortcut, number)
        val restTemplate = RestTemplate()
        val url = "$urlIndex/part/{part}"
        val response = restTemplate.getForObject(url, List::class.java, part)
        //map the JSON response to values
        if (response != null && response.isNotEmpty()) {
            for (partRecord in response) {
                if (partRecord is Map<*, *>) {
                    partItem = partRecord[partItemKind].toString()
                }
            }
        }
        //println("For part $partItemKind is  $part : $partItem")

        return partItem
    }
}









