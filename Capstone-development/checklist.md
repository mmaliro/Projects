
# Dev10 Field Agent Deluxe

_Define more tasks as they're identified_

## General

* [x] Add people to the private repo
* [x] Review the plan
* [x] Set up and test the backend
  * [x] Create the database
  * [x] Start the API and send a test HTTP request

## React Project Setup

* [x] Create a new React project
* [x] Add React Router
* [x] Add React Bootstrap
* [x] Stub out top level routes and components

#### Bootstrap Setup

* [x] Add base Bootstrap layout and classes
* [x] Create a menu
* [ ] Update menu to be mobile friendly
* [ ] Update layout to be fluid

#### Custom Components

* [x] Create a Heading component
* [ ] Update the Heading component to render `props.children`
* [ ] Create an Errors component
* [ ] Create a Form component
  * Use React.Children to map over the children to add value properties and change handlers?
  * Use a render prop to render the form content so that each field can have its value set and a change handler wired up?
* [ ] Create a FormField component

#### Misc

* [ ] Refactor all fetches to services

## Pages

### Agencies

* [x] Display a list of agencies
* [x] Include button to add an agency
* [x] Include button to view each agency
* [ ] Include button to delete each agency

### Agency Detail

* [x] Display the detail for a single agency (no editing)
* [x] Include button to edit the base agency information
* [ ] Include button to delete the agency (includes the fetch)
* [x] Gracefully handle when an agency doesn't have locations or agents
* [ ] Gracefully handle an unknown agency ID
* [ ] Only fetch the Agency data once
  * Will need a way to keep the agency data current as locations and agents change
  * Define the sub routes within the `App` component? Or create a parent component to control when the agency detail, add agency, edit agency, agency location, etc. are displayed?
  * Use a nested `<Routes>` element to define nested routes
  * The `<Outlet>` component defines where content will live

#### Agency Detail Locations

* [x] Display the agency's locations (if available)
* [x] Include a button to add a location (for the current agency)
* [x] Include buttons to edit each location (for the current agency)
* [ ] Include buttons to delete each location (for the current agency... includes the fetch)

#### Agency Detail Agents

* [x] Displays the agency's agents (if available)
* [x] Include a button to add an agent
* [ ] Include buttons to edit each agency/agent
* [ ] Include buttons to delete each agency/agent
* [ ] Include buttons to edit each agent (for convenience)?

### Add/Edit Agency

* [ ] Display a form to add an agency
* [ ] Display a form to edit an agency

### Add/Edit Location

* [x] Display a form to add an agency location
* [ ] Display a form to edit an agency location

### Add/Edit Agency Agent

* [ ] Create a component to add an agency agent
  * [ ] Implement an agent search function to select the agent to add
* [ ] Create a component to edit an agency agent

---

### Agents

* [ ] Display a list of agents
* [ ] Include button to add an agent
* [ ] Include buttons to edit and delete each agent

### Agent Detail and/or Add/Edit Agent

_Demonstrate an alternative approach... allow the user to add/edit all aspects of an agent (including agency relationships and aliases) so the user can save everything at one time_

---

_Might not get to these items..._

### Security Clearances

* [ ] Display a list of security clearances
* [ ] Include button to add a security clearance
* [ ] Include buttons to edit and delete each security clearance
  * Update the API to include a reference count for each security clearance
  * Use this count to enable/disable the delete buttons

### Add/Edit Security Clearance

* [ ] Display a form to add/edit a security clearance

### Missions

_TODO_ implement missions (leave this for associates to implement based upon the patterns shown elsewhere?)

_Note: The API does not implement missions... so the backend would need to be updated to support missions_

### Security

_TODO_ implement security in both the backend and frontend???

### Deployment

_TODO_ deploy the project to AWS???
