import React, { useState } from 'react';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
  });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

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
          ...(isLogin ? {} : { email: formData.email }),
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'An error occurred');
      }

      const contentType = response.headers.get('Content-Type');
      let data;

      if (contentType && contentType.includes('application/json')) {
        data = await response.json();
        setMessage(data.message || 'Success!');
      } else {
        data = await response.text();
        setMessage('Login successful!');
        localStorage.setItem('token', data);
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