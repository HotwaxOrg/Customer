// Importing required packages for implementations
import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityCondition // To implement condition
import org.moqui.entity.EntityFind // To implement find
import org.moqui.entity.EntityList //To get the list of EntityValue objects
import org.moqui.entity.EntityValue // To access a single record in the database

ExecutionContext ec =  context.ec // Getting the execution context

// Finding our Customer View entity
EntityFind ef = ec.entity.find('customer.findcustomer.FindCustomerView')

ef.selectField("partyId")

// Getting all list of records based on partyId
//if (partyId) {
//    ef.condition(
//            ec.entity.conditionFactory.makeCondition(
//                    "partyId", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + partyId + "%")
//                    .ignoreCase())
//}

if (firstName) {
    ef.condition(
            ec.entity.conditionFactory.makeCondition(
                    "firstName", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + firstName + "%")
                    .ignoreCase())
}
if (lastName) {
    ef.condition(
            ec.entity.conditionFactory.makeCondition(
                    "lastName", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + lastName + "%")
                    .ignoreCase())
}
if (infoString){
    ef.condition("infoString", EntityCondition.EQUALS, infoString)
}



EntityList el = ef.list() // Getting the list of EntityValue objects

// Initialize an empty list to store the partyId values
partyIdList = []

// Iterate over the EntityList and add partyId values to the list
for (EntityValue ev in el) {
    partyIdList.add(ev.partyId)
}