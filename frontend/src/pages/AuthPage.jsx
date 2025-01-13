import React, { useState } from 'react';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div className="d-flex align-items-center justify-content-center vh-100">
      <div className="card p-4" style={{ maxWidth: '400px', width: '100%' }}>
        <h3 className="text-center mb-4">{isLogin ? 'Login' : 'Register'}</h3>
        <form>
          <div className="mb-3">
            <label className="form-label">Username</label>
            <input type="text" className="form-control" placeholder="Enter your username" required />
          </div>
          <div className="mb-3">
            <label className="form-label">Password</label>
            <input type="password" className="form-control" placeholder="Enter your password" required />
          </div>
          {!isLogin && (
            <div className="mb-3">
              <label className="form-label">Email</label>
              <input type="email" className="form-control" placeholder="Enter your email" required />
            </div>
          )}
          <button className="btn btn-primary w-100">{isLogin ? 'Login' : 'Register'}</button>
        </form>
        <button
          className="btn btn-link mt-3 w-100"
          onClick={() => setIsLogin(!isLogin)}
        >
          {isLogin ? 'Switch to Register' : 'Switch to Login'}
        </button>
      </div>
    </div>
  );
};

export default AuthPage;
