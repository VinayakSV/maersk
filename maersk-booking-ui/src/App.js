import React from 'react';
import BookingForm from './components/BookingForm';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Maersk Container Booking</h1>
      </header>
      <main>
        <BookingForm />
      </main>
    </div>
  );
}

export default App;
