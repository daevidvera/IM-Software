import React, { useState } from 'react';
import axios from 'axios';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    try {
      const res = await axios.post(`http://localhost:8080/req/reset-password`, null, {
        params: { email }
      });

      setIsSubmitting(true);
      setMessage(res.data.message || "Reset link sent to your email.");
      setTimeout(() => {
      window.close(); // tab close in 2 seconds
    }, 2000);
    } catch (err) {
      const errMsg =
        typeof err.response?.data === 'string'
          ? err.response.data
          : err.response?.data?.error || "Error sending reset link";
      setError(errMsg);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
      <form onSubmit={handleSubmit} className="text-center" style={{ width: '300px' }}>
        <h2 className="title col-12">Password Reset.</h2>
        <div className="mb-3">
          <input
            type="email"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="form-control border-color"
            placeholder="Enter your email"
            required
          />
        </div>
        <button type="submit" className="btn-color dark w-100 mb-3" disabled={isSubmitting}> {isSubmitting ? 'Submitting...' : 'Reset Password Mail'}</button>
        {message && <p className="text-success mt-3">{message}</p>}
        {error && <p className="text-danger mt-3">{error}</p>}
      </form>
    </div>
  );
};

export default ForgotPassword;
