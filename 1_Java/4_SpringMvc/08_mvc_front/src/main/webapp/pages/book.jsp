<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/vue.js"></script>
    <script src="../js/axios.js"></script>
</head>
<body>
    <div id="app">
        <button @click="testGetAll()">OK</button>
        <div v-if="person !== null">
            <h2>{{person.name}}</h2>
            <h2>{{person.age}}</h2>
        </div>
    </div>
    <script type="text/javascript">
        Vue.config.productionTip = false;
        new Vue({
            el: '#app',
            data: {
                result: "",
                person: null,
            },
            methods: {
                testPost() {
                    axios.post("/books").then((res) => {
                        this.result = res.data;
                    });
                },
                testGetAll() {
                    axios.get("/books").then((res) => {
                        this.person = res.data;
                    });
                }
            }
        })
    </script>
</body>
</html>
