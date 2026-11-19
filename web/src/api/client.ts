import axios from 'axios';

const API_BASE_URL = (import.meta as any).env?.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Real API helper methods
export const api = {
  getProducts: () => apiClient.get('/products'),
  getProductById: (id: number) => apiClient.get(`/products/${id}`),
  searchProducts: (q: string) => apiClient.get(`/products/search?q=${encodeURIComponent(q)}`),
  createOrder: (orderData: any) => apiClient.post('/orders', orderData),
  createPaymentOrder: (paymentData: any) => apiClient.post('/payments/create-order', paymentData),
  verifyPayment: (verificationData: any) => apiClient.post('/payments/verify', verificationData),
  getSellerProfile: () => apiClient.get('/sellers/me'),
  registerSeller: (sellerData: any) => apiClient.post('/sellers/register', sellerData),
  getAdminAnalytics: () => apiClient.get('/admin/analytics'),
};
