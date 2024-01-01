const { default: axios } = require("axios");

const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",
});

export default axiosInstance;