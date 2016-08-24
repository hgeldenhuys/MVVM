# MVVM
MVVM is a pattern that facilitates separation of the front-end model from the business logic or domain model. The view-model can be seen as the converter, aspect or augmentation of your original domain model entity. It exists so that we can easily customize our model for UI representation without contaminating our domain model.

 

## Analogous to MVC
Traditionally the pattern most developers are familiar with, is MVC or model-view-controller but cannot be achieved in the way Mendix modeling works. Since Mendix itself implies the model in the view, it is important to layer our app with different Modules to achieve a pattern where we separate our domain from the UI.

Example, virtual attributes are usually a UI concern, or a conditional visibility and has no real benefit at the Domain Model level. We should avoid bringing this into our core Domain Model, for security, performance, quality and maintainability reasons:

BI typically is only interested in Domain models.
When we extend our app with more functionality, the domain model tends to snowball with layers and layers of new functionality.
The domain model performance can be impeded by security XPaths, virtual attributes etc

## Model

This is your data access layer. You would mostly use Domain Specific concepts or “language” here (DSL). To describe a pure uncluttered model

## View

Like MVC, the view is your UI layer. This is almost exclusively bound to forms, snippets and other UI elements

## View Model

This abstract model exposes parts of your domain model and synchronizes the data back to your domain model. This could contain application logic*, which is different from domain logic

 *Business logic usually can be grouped into domain logic, and application/UI logic.
 
## Benefits of the MVVM pattern
- Clean Domain Model
- Clearly defined aspects (View Models)
- Fast processing on domain model
 - Remove calculated attributes
 - Move XPath security constraints to VM
- Improved quality due to demarcated concerns
- Searchable attributes on VM replace Virtual attrs on Domain model
- Control the state outside of the Domain Model
- Save points are now possible
-Secure domain model from XAS exploitation

## Usage

### Reusing same attributes in VM

You can copy-paste fields from the Model to the View-Model and have automatic reuse. This is the most straightforward usage.

You can now add application logic like validation on the attributes of the VM.

## Flow of updates

Using same-fields from the Model on the ViewModel has automatically propagates to the domain model. When you want to control dataflow however, you will use fields with different names and commit them on a Before Commit Event Handler of you VM.

Other advantages include things like once you save a page, you can save your VM without saving to the Model. For example, I’ve entered 20 of 40 fields on a page, I can hit save and come back to it and submit when the form is complete.

Keep in mind that once the domain Model is successfully saved, this triggers updating all the ViewModels except the ViewModel that triggered the save to prevent infinite recursion.

## Rules of MVVM

- Never use the Model on forms, except when reading data by association from the view and you don’t care about XPath security constraints for read, Rather use the VM on pages
- A viewModel should have a 1-1 association to a single Model, but a Model can have many different kinds of View Models. The ViewModel can be associated to other Non-Model entities with any multiplicity.
- Views cannot be specialized (Generalization)
Be careful that View Models don’t “fight” over shared concerns if they are used on the same page
Manual updating of Views Models

In some cases you might want to retroactively update views or models when committing without event handlers were used, or when bulk data was loaded outside of Mendix.

In that case, 3 API microflows are created to this purpose:

- RefreshModel : Refreshes the Model from a single view
- RefreshViews : Updates views from the model
- ReinitializeViews : Creates views for a model that was created without it’s views

## Demo time

Nothing illustrates an idea like a Youtube video. So, that’s why we created a small Vlog to demonstrate how to use the MVVM pattern, using a basic CRM application where we have a core domain model, but 2 different aspects (or feature sets), namely Contactability and Product Eligibility.
