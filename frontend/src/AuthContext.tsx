import { createContext, useContext, useState } from "react";
import type { ReactNode } from "react";

interface AuthContextType {
  token: string | null;
  role: string | null;
  login: (token: string) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(localStorage.getItem("medagenda_token"));

  const parseRole = (jwt: string): string | null => {
    try {
      const payload = JSON.parse(atob(jwt.split('.')[1]));
      return payload.role || null;
    } catch {
      return null;
    }
  };

  const login = (newToken: string) => {
    localStorage.setItem("medagenda_token", newToken);
    setToken(newToken);
  };

  const logout = () => {
    localStorage.removeItem("medagenda_token");
    setToken(null);
  };

  return (
    <AuthContext.Provider value={{ token, role: token ? parseRole(token) : null, login, logout, isAuthenticated: !!token }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used inside an AuthProvider");
  return context;
};
