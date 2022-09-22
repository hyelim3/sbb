package com.mysite.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    int increaseNum = -1;

    @RequestMapping("/sbb")
    @ResponseBody
    public String index() {
        System.out.println("sbb");
        return "sbb";
    }

    //@GetMapping, @PostMapping
    @GetMapping("/page1")
    @ResponseBody
    public String showPage1() {
        return """
                <form method="POST" action="/page2">
                    <input type='number' name="age" placeholder="나이"/>
                    <input type="submit" value="page2로 POST방식으로 이동" />
                </form>
                """;
    }

    //@RequestParam
    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(value = "age", defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>POST방식으로 옴</h1>
                """.formatted(age);
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(value = "age", defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>GET방식으로 옴</h1>
                """.formatted(age);
    }

//    @GetMapping("/plus")
//    @ResponseBody
//    public int showplusGet(int a, int b) {
//        return a + b;
//    }

    //plus, minus, increase
    @GetMapping("/plus")
    @ResponseBody
    public String plus(@RequestParam(required = false) Integer a, @RequestParam(required = false) Integer b) {
        if (a == null) {
            return "a를 입력해주세요.";
        }
        if (b == null) {
            return "b를 입력해주세요.";
        }
        return String.valueOf(a + b);
    }

    @GetMapping("/minus")
    @ResponseBody
    public String minus(@RequestParam(required = false) Integer a, @RequestParam(required = false) Integer b) {
        if (a == null) {
            return "a를 입력해주세요.";
        }
        if (b == null) {
            return "b를 입력해주세요.";
        }
        return String.valueOf(a - b);
    }

    //새로고침하면 숫자증가
    @GetMapping("/increase")
    @ResponseBody
    public int increase() {

        return increaseNum++;
    }

    //구구단 출력
    @GetMapping("/gugudan")
    @ResponseBody
    public String gugudan(int dan, int limit) {
        //문자열 합치기
        String result = "";
        for (int i = 1; i <= limit; i++) {
            result += (dan + " * " + i + " = " + dan * i + "<br/>\n");
        }
        return result;
    }

    @GetMapping("/saveSessionAge")
    @ResponseBody
//    public void saveSession(@RequestParam("age") int age, HttpServletRequest req) {
//        System.out.println("age : " + age);
//        HttpSession session = req.getSession();
//        session.setAttribute("age", age);
//    }
    public String saveSession(@RequestParam("age") int age, HttpSession session) {
        System.out.println("age : " + age);
        session.setAttribute("age", age);
        return "나이 %d살이 세션에 저장되었습니다.".formatted(age);
    }

    @GetMapping("/getSessionAge")
    @ResponseBody
    public String saveSession(HttpSession session, HttpServletResponse res) {
        int age = (int) session.getAttribute("age");
        Cookie cookie = new Cookie("age", String.valueOf(age));
        res.addCookie(cookie);
        return "세션에 저장된 나이는 %d입니다.".formatted(age);
    }

    //9/22 수업
    @GetMapping("/addPerson/{id}/{age}/{name}")
    @ResponseBody
    public Person addPerson(Person person, @PathVariable("id") Integer id, @PathVariable("age") Integer age, @PathVariable("name") String name) {
      return person;
    }
    @Getter
    @AllArgsConstructor
    class Person{
        private int id;
        private int age;
        private String name;

        //lombok - > @AllArgsConstructor -> 매개변수 다 들어있는 생성자 만들기
//       public Person(int id, int age, String name){
//           this.id = id;
//           this.age = age;
//           this.name = name;
//       }
    }


}
















