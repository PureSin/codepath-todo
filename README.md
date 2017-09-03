# Pre-work - TodoApp

TodoApp is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Kelvin Ma

Time spent: 6 hours

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file (using Room)
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView) (ReclyclerView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Uses Room for SQL persistence Commit: a8a70621f20fc97373926d0840d73d78ec40d92c
* [X] Additional fields (description)
* [X] Custom adapter using Recycler view
* [x] ButterKnife to easier view binding Commit: 389766de710a750975d3e5e3fa360b9ed4c3cca9
* [x] Uses constraint layout

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://raw.githubusercontent.com/PureSin/codepath-todo/master/codepath.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Overall I like the Android framework compared to my experiences with iOS/Web/Java Swing desktop apps.

The layout/UI system xml system is usable with constraint layout and the Android Studio layout editor tool. It's not as easy
to write as web html/css/js but certainly better than Swing XML and its tools. Roughly on par with iOS in terms of learning curve.

One of example of the layout being more complicated than web is the spinner (drop down) element. It
doesn't seem like you're able to specify all choices purely in XML.


**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work.
How would you describe an adapter in this context and what is its function in Android?
Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:**: Like the name suggests, it follows the "Adapter" pattern and adapters a datasource (cursor or array in this case)
to meet the interface of the ListView. The adapter enables a level of abstraction between the model of the app and its presentation
as a list of UI elements.

The `convertView` method adapters an instance of the model (Task in our case) to a inflated Android layout.

## Notes

Describe any challenges encountered while building the app.

** Using Room **
Using room was mostly painless experience and certainly a lot better than writing SQL queries directly.
Still I ran into small road blocks with regards to updating the DB version when changing the Task model.
Also since I made priority field an enum, I had to declare a TypeConverters class for Room to know how to handle the
conversion.

** RecyclerView Adapter **
I spend almost an hour switching from the custom list view adapter to RecyclerView. The main issue is because
I was redefining the model I passed into the adapter instead of updating the list of tasks.

It's the difference between: `myTasks = getAllTasks...` vs `myTasks.clear(), myTasks.add(getAllTasks())`

The RecyclerView adapter only holds a reference to the list of tasks it was passed in the constructor. So I should
keep the same reference as I update the list and then call notify on the adapter.

** Click Handling with RecyclerView **

With RecyclerView, the recommended way to handle UI interactions is for the view holder to be the listener. So I had to
move the click handlers into view holder and change the way it works.

** Setting Activity Result **

I had some issues getting the edit task to correctly pass the changed Task object back. I was calling setResult but not finish so
clicking "Save Changes" didn't trigger `onActivityResult` in MainActivity.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

