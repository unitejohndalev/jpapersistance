import { Routes, Route, Navigate } from "react-router-dom";
import AdminDashboard from "../components/admin/adminDashboard/AdminDashboard";
import { LoginForm } from "../components/login/Login";
import { Recharts } from "../components/recharts/Recharts";
import { RegisterForm } from "../components/register/Register";
import useFetchOAuth2User from "../customHook/userFetchOAuth2User";
import { useAuth } from "../context/AuthContext";
import { useEffect } from "react";

export const AuthRoutes = () => {
    const { isAuthenticated } = useAuth(); // Accessing authentication status from AuthContext
    const location = useLocation();
    const userRole = localStorage.getItem('userRole'); // Retrieve the user role from localStorage
    const navigate = useNavigate()
  
    useFetchOAuth2User();

    useEffect(() => {
        // If the user is authenticated and tries to access an invalid route based on their role
        if (isAuthenticated) {
            if (userRole === 'ROLE_USER' && location.pathname !== '/home') {
                // If a ROLE_USER tries to access a different route, redirect to /home
                navigate('/home');
            } else if (userRole === 'ROLE_ADMIN' && location.pathname !== '/admin/dashboard') {
                // If a ROLE_ADMIN tries to access a different route, redirect to /admin/dashboard
                navigate('/admin/dashboard');
            }
        }
    }, [ isAuthenticated, location.pathname, userRole ]);

    return (
        <Routes>
            {/* Public routes */}
            {!isAuthenticated ? (
                <>
                    <Route path="/" element={<LoginForm />} />
                    <Route path="/register" element={<RegisterForm />} />
                    <Route path="*" element={<Navigate to="/" />} />
                </>
            ) : (
                <>
                    {/* Protected routes based on role */}
                    {userRole === 'ROLE_USER' && (
                        <>
                        <Route path="/home" element={<Recharts />} />
                            <Route path="*" element={<Navigate to="/home" />} /> {/* Ensure ROLE_USER stays on /home */}
                        </>
                    )}
                    {userRole === 'ROLE_ADMIN' && (
                        <>
                    <Route path="/admin/dashboard" element={<AdminDashboard />} />
                            <Route path="*" element={<Navigate to="/admin/dashboard" />} /> {/* Ensure ROLE_ADMIN stays on /admin/dashboard */}
                        </>
                    )}
                </>
            )}
        </Routes>
    );
};