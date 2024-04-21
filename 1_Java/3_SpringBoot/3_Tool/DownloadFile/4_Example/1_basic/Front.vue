<template>
  <button @click="downloadReq">OK</button>
</template>

<script>
import Axios from "axios";

export default {
  methods: {
    downloadReq() {
      Axios.post("http://127.0.0.1:9000/helloWorld", {}, {
        responseType: "blob",
      }).then((response) => {
        const fileName = "front.html";
        const blob = new Blob([response.data]);
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", fileName);
        document.body.appendChild(link);
        link.click(); // 執行下載
        window.URL.revokeObjectURL(url); // 釋放url
        document.body.removeChild(link); // 釋放標籤
      }).catch(() => {
        console.log("下載失敗");
      });
    }
  },
};
</script>
