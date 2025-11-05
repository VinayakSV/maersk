import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:9090/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const checkAvailability = (bookingData) => {
  return apiClient.post('/bookings/check-available', bookingData);
};

export const createBooking = (bookingData) => {
  // Add the current timestamp for the booking record
  const bookingPayload = {
    ...bookingData,
    timestamp: new Date().toISOString(),
  };
  return apiClient.post('/bookings', bookingPayload);
};
