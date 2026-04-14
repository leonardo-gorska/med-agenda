import { Box, Button, Container, Flex, Heading, Input, Text, VStack } from "@chakra-ui/react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../AuthContext";
import { api } from "../api";
import { Activity } from "lucide-react";
import { toaster } from "@/components/ui/toaster";

export function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await api.post("/auth/login", { email, password });
      if (response.data.token) {
        login(response.data.token);
        toaster.create({ title: "Bem-vindo de volta!", type: "success" });
        navigate("/dashboard");
      }
    } catch (err: any) {
        toaster.create({
          title: "Erro de autenticação",
          description: "Credenciais inválidas ou erro no servidor.",
          type: "error",
        });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box minH="100vh" bgGradient="to-br" gradientFrom="gray.50" gradientTo="teal.50" display="flex" alignItems="center" justifyContent="center" p={4}>
      <Container maxW="md">
        <Box bg="white/80" backdropFilter="blur(24px)" shadow="2xl" rounded="3xl" p={{ base: 8, md: 10 }} border="1px solid" borderColor="white">
          <VStack gap={8} align="stretch">
            <Flex direction="column" align="center" gap={3}>
              <Box p={4} bg="teal.50" rounded="2xl" color="teal.500">
                <Activity size={32} />
              </Box>
              <Heading size="2xl" color="gray.800">Entrar</Heading>
              <Text color="gray.500" textAlign="center">Acesse a plataforma segura do MedAgenda.</Text>
            </Flex>

            <form onSubmit={handleLogin}>
              <VStack gap={5}>
                <Box w="full">
                  <Text mb={2} fontSize="sm" fontWeight="medium" color="gray.700">E-mail</Text>
                  <Input type="email" placeholder="seu@email.com" bg="white" size="lg" value={email} onChange={(e) => setEmail(e.target.value)} required rounded="xl" />
                </Box>
                <Box w="full">
                  <Text mb={2} fontSize="sm" fontWeight="medium" color="gray.700">Senha</Text>
                  <Input type="password" placeholder="••••••••" bg="white" size="lg" value={password} onChange={(e) => setPassword(e.target.value)} required rounded="xl" />
                </Box>
                <Button type="submit" colorPalette="teal" size="lg" w="full" mt={2} rounded="xl" loading={loading} shadow="md">
                  Acessar Conta
                </Button>
              </VStack>
            </form>

            <Text textAlign="center" fontSize="sm" color="gray.500">
              Não possui conta? <Text as="span" color="teal.600" fontWeight="bold"><Link to="/">Voltar ao Início</Link></Text>
            </Text>
          </VStack>
        </Box>
      </Container>
    </Box>
  );
}
