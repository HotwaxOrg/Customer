// Importing required packages for implementations
import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityCondition // To implement condition
import org.moqui.entity.EntityFind // To implement find
import org.moqui.entity.EntityList //To get the list of EntityValue objects
import org.moqui.entity.EntityValue // To access a single record in the database

ExecutionContext ec =  context.ec // Getting the execution context

// Checking if our email address already exist in contact mech entity
EntityFind ef_cm = ec.entity.find('customer.contactmech.ContactMech')

if (emailAddress){
    ef_cm.condition("infoString", EntityCondition.EQUALS, emailAddress)
}
EntityValue ev_cm = ef_cm.one()
if (ev_cm){
    ec.message.addError("Record already exists")
}else{
    //    If email address doesn't exist

    //    Creating a record in the party entity
    String partyId = ec.entity.sequencedIdPrimary("customer.party.Party", null, null)
    partyId = 'PER' + partyId
    EntityValue ev_pe = ec.entity.makeValue('customer.party.Party')
    ev_pe.set('partyId', partyId)
    ev_pe.set('partyTypeEnumId', 'PtyPerson')
    ev_pe.create()

    // Creating a record in the person entity
    EntityValue ev_per = ec.entity.makeValue('customer.person.Person')
    ev_per.set('partyId', partyId)
    ev_per.set('firstName', firstName)
    ev_per.set('lastName', lastName)
    ev_per.create()

    //    Creating a record in party role
    EntityValue ev_pr = ec.entity.makeValue('customer.partyrole.PartyRole')
    ev_pr.set('partyId', partyId)
    ev_pr.set('roleTypeId', 'CUSTOMER')
    ev_pr.create()

    // Creating a record in contact mech
    EntityValue ev_cm_1 = ec.entity.makeValue('customer.contactmech.ContactMech')
    def contactMechId = ec.entity.sequencedIdPrimary('customer.contactmech.ContactMech', null, null)
    ev_cm_1.set('contactMechId', contactMechId)
    ev_cm_1.set('contactMechTypeEnumId', 'ctyEmailAddress')
    ev_cm_1.set('infoString', emailAddress)
    ev_cm_1.create()

    // Creating a record in party contact mech
    EntityValue ev_pcm = ec.entity.makeValue('customer.partycontactmech.PartyContactMech')
    ev_pcm.set('partyId', partyId)
    ev_pcm.set('contactMechId', contactMechId)
    ev_pcm.set('contactMechPurposeId', 'EmailPrimary')
    ev_pcm.set('fromDate', System.currentTimeMillis())
    ev_pcm.create()
}