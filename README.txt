- HelpMEController :: END Point
- Read Request from respective Request beans.
	- Apply validations at request beans
- DB tables are represented by annotation @Entity :: refer com.helpme.model
- Action classes/Services are at service package :: Business logic should be present at this classes methods.
	- To run transaction :: annotate @Transactional at method level
- Repository represents the DAO layer, create methods in this interface to access Entities.
- ResponseBean is abstract and generic.
	- If want to send custom response, extend this class and define the members accordingly.