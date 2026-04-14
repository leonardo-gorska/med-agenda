import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080", // Assume the backend runs here
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("medagenda_token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
