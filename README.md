# _{Application Name}_

#### _{Brief description of application}, {Date of current version}_

#### By _**{List of contributors}**_

## Description

_{Currently finished testing for Restaraunt class, next up will be the last class. When having bugs be sure to look at database for oddentities and if its a null pointer expection, that means that we want reference thats not there. Be sure to impament the DOA from the database(sql) to page. }_

## Setup/Installation Requirements

* _This is a great place_
* _to list setup instructions_
* _in a simple_
* _easy-to-understand_
* _format_

![Database schema](/src/main/resources/public/images/dbschema.jpg "Schema")

_One of the main problems while deugging today was that we edited our database and didn't remove the cache using the rm -f command on terminal.Always be sure to double check what is in your database and if there is a null value and all test are running try removing the database.

## Known Bugs

To Remove expired database, open terminal check for databate with ls ... after finding name: rm -f name.db.*
(force removal of all db files of a given name)

_//get: show new task form
         get("/tasks/new", (req, res) -> {
             Map<String, Object> model = new HashMap<>();
             List<Category> allCategories = categoryDao.getAll();
             model.put("categories", allCategories);
             return new ModelAndView(model, "task-form.hbs");
         }, new HandlebarsTemplateEngine());

         //post: process new task form
         post("/tasks/new", (request, response) -> {
             Map<String, Object> model = new HashMap<>();
             List<Category> allCategories = categoryDao.getAll();
             model.put("categories", allCategories);
             String description = request.queryParams("description");
             int categoryId = Integer.parseInt(request.queryParams("category"));
             Task newTask = new Task(description, categoryId);
             taskDao.add(newTask);
             model.put("task", newTask);
             return new ModelAndView(model, "success.hbs");
         }, new HandlebarsTemplateEngine());}_

## Support and contact details

_{Let people know what to do if they run into any issues or have questions, ideas or concerns.  Encourage them to contact you or make a contribution to the code.}_

## Technologies Used

_{Tell me about the languages and tools you used to create this app. Assume that I know you probably used HTML and CSS. If you did something really cool using only HTML, point that out.}_

### License

*{Determine the license under which this application can be used.  See below for more details on licensing.}*

Copyright (c) 2016 **_{List of contributors or company name}_**