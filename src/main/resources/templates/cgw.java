           get("/removeemp", (request,response) -> {
                  Map map = new HashMap();
                  map.put("id",request.queryParams("empid"));

                  q.deleteEmploye(Integer.parseInt(request.queryParams("empid")));

              return new ModelAndView(map , "templates/geranthome.vtl");
           },new VelocityTemplateEngine());


           ///////////////////////////////////////


           /////////////////Add employ///////////////
             get("/addemp", (request,response) -> {
              Map map = new HashMap();
              
              return new ModelAndView(map , "templates/addemp.vtl");

           },new VelocityTemplateEngine());


           ///////////////////////////////////////


           post("/addEmpRedirect", (request,response) -> {

              HashMap <String,String>map = new HashMap<String,String>();
            
      
              q.addEmploye(request.queryParams("username"), request.queryParams("firstname"),request.queryParams("lastname"),request.queryParams("pword"));

              return new ModelAndView(map , "templates/removeemp.vtl");

           },new VelocityTemplateEngine());

           /////////////////////////////////////