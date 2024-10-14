import { AuthProvider } from "./context/AuthContext";
import { AuthRoutes } from "./routes/AuthRoutes";


function App() {



  return (
    <>
    <AuthProvider>
        <AuthRoutes />
    </AuthProvider>
    </>
  )
}

export default App
