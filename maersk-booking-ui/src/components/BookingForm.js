import React, { useState } from 'react';
import { checkAvailability, createBooking } from '../services/api';
import './BookingForm.css';

const BookingForm = () => {
  const [formData, setFormData] = useState({
    containerSize: 20,
    containerType: 'DRY',
    origin: 'Southampton',
    destination: 'Singapore',
    quantity: 5,
  });
  const [loading, setLoading] = useState(false);
  const [apiResponse, setApiResponse] = useState(null);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleCheckAvailability = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setApiResponse(null);
    try {
      const response = await checkAvailability(formData);
      setApiResponse({ type: 'availability', data: response.data });
    } catch (err) {
      setError(err.response?.data?.message || 'An error occurred.');
    } finally {
      setLoading(false);
    }
  };

  const handleCreateBooking = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setApiResponse(null);
    try {
      const response = await createBooking(formData);
      setApiResponse({ type: 'booking', data: response.data });
    } catch (err) {
      setError(err.response?.data?.message || 'An error occurred.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="booking-form-container">
      <form>
        <div className="form-group">
          <label>Container Size</label>
          <select name="containerSize" value={formData.containerSize} onChange={handleChange}>
            <option value={20}>20</option>
            <option value={40}>40</option>
          </select>
        </div>
        <div className="form-group">
          <label>Container Type</label>
          <select name="containerType" value={formData.containerType} onChange={handleChange}>
            <option value="DRY">DRY</option>
            <option value="REEFER">REEFER</option>
          </select>
        </div>
        <div className="form-group">
          <label>Origin</label>
          <input type="text" name="origin" value={formData.origin} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Destination</label>
          <input type="text" name="destination" value={formData.destination} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Quantity</label>
          <input type="number" name="quantity" value={formData.quantity} onChange={handleChange} />
        </div>
        <div className="form-actions">
          <button onClick={handleCheckAvailability} disabled={loading}>
            {loading ? 'Checking...' : 'Check Availability'}
          </button>
          <button onClick={handleCreateBooking} disabled={loading}>
            {loading ? 'Booking...' : 'Create Booking'}
          </button>
        </div>
      </form>
      {error && <div className="error-message">{error}</div>}
      {apiResponse && (
        <div className="response-message">
          {apiResponse.type === 'availability' && (
            <p>Space Available: <strong>{apiResponse.data.available ? 'Yes' : 'No'}</strong></p>
          )}
          {apiResponse.type === 'booking' && (
            <p>Booking successful! Reference ID: <strong>{apiResponse.data.bookingRef}</strong></p>
          )}
        </div>
      )}
    </div>
  );
};

export default BookingForm;
