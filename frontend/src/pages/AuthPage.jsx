import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for redirection

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
    role: '',
    phoneNumber: '',
    address: '',
    companyName: '',
  });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate(); // Initialize useNavigate

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    const endpoint = isLogin
      ? 'http://localhost:8080/auth/login'
      : 'http://localhost:8080/auth/register';

    try {
      const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: formData.username,
          password: formData.password,
          email: formData.email,
          role: formData.role,
          phoneNumber: formData.phoneNumber,
          address: formData.address,
          companyName: formData.companyName,
          ...(isLogin ? {} : { email: formData.email }),
        }),
      });


      const data = await response.json();

      if (isLogin) {
        // Store the token in localStorage
        localStorage.setItem('token', data.token);
          
          setTimeout(() => {
            navigate(data.links.redirect);
          }, 100); // Small delay to ensure token is fully written
        

        // Redirect the user based on the redirect link
        if (data.links?.redirect) {
          console.log("navigating to:", data.links.redirect)
          navigate(data.links.redirect); // Use the redirect route provided by the backend
        } else {
          setMessage('Etwas ist schiefgelaufen');
        }
      } else {
        setMessage('Registration successful!');
      }

      setFormData({ username: '', password: '', email: '' });
    } catch (err) {
      console.error(err);
      setError(err.message || 'Network error. Please try again later.');
    }
  };

  return (
    <div className="d-flex align-items-center justify-content-center vh-100">
      <div className="card p-4" style={{ maxWidth: '400px', width: '100%' }}>
        <h3 className="text-center mb-4">{isLogin ? 'Login' : 'Register'}</h3>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label">Username</label>
            <input
              type="text"
              className="form-control"
              placeholder="Enter your username"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter your password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          {!isLogin && (
              <div className="mb-3">
                <label className="form-label">Phone Number</label>
                <input
                  type="tel"
                  className="form-control"
                  placeholder="Enter your phone number"
                  name="phoneNumber"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  required
                />
              </div>
          )}
          {!isLogin && (
              <div className="mb-3">
                <label className="form-label">Address</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter your address"
                  name="address"
                  value={formData.address}
                  onChange={handleChange}
                  required
                />          
              </div>
          )}
          {!isLogin && (
            <div className="mb-3">
              <label className="form-label">Email</label>
              <input
                type="email"
                className="form-control"
                placeholder="Enter your email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
              />
            </div>
          )}
          {!isLogin && (
              <div className="mb-3">
                <label className="form-label">Company Name</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter your company name"
                  name="companyName"
                  value={formData.companyName}
                  onChange={handleChange}
                  required
                />
              </div>
          )}
          <button type="submit" className="btn btn-primary w-100">
            {isLogin ? 'Login' : 'Register'}
          </button>
        </form>
        <button
          className="btn btn-link mt-3 w-100"
          onClick={() => {
            setIsLogin(!isLogin);
            setMessage('');
            setError('');
            setFormData({ username: '', password: '', email: '' });
          }}
        >
          {isLogin ? 'Switch to Register' : 'Switch to Login'}
        </button>
        {message && <p className="text-success mt-3 text-center">{message}</p>}
        {error && <p className="text-danger mt-3 text-center">{error}</p>}
      </div>
    </div>
  );
};

export default AuthPage;
