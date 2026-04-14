import { Box, Container, Flex, Heading, Text, Button, Table, Badge, HStack } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../AuthContext";
import { api } from "../api";
import { Calendar, User, LogOut, Plus } from "lucide-react";
import { toaster } from "@/components/ui/toaster";

interface Appointment {
  id: number;
  doctorId: number;
  patientId: number;
  doctorName: string;
  patientName: string;
  startTime: string;
  endTime: string;
  status: string;
}

export function Dashboard() {
  const { logout, role } = useAuth();
  const navigate = useNavigate();
  const [appointments, setAppointments] = useState<Appointment[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      setLoading(true);
      const res = await api.get("/appointments");
      setAppointments(res.data.content || []);
    } catch (err) {
      toaster.create({ title: "Erro ao buscar agendamentos", type: "error" });
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <Box minH="100vh" bg="gray.50" fontFamily="'Inter', sans-serif">
      {/* Sidebar / Navbar */}
      <Flex as="nav" align="center" justify="space-between" px={10} py={4} bg="white" borderBottom="1px solid" borderColor="gray.200" shadow="sm">
        <HStack gap={3}>
          <Box p={2} bg="teal.100" rounded="md" color="teal.600"><Calendar size={20} /></Box>
          <Heading size="md" color="gray.800">Painel do {role === "ROLE_DOCTOR" ? "Médico" : "Paciente"}</Heading>
        </HStack>
        <HStack gap={4}>
          <Button variant="ghost" colorScheme="gray" onClick={handleLogout} size="sm">
            Sair <LogOut size={16} style={{ marginLeft: "6px" }} />
          </Button>
        </HStack>
      </Flex>

      <Container maxW="7xl" py={10}>
        <Flex justify="space-between" align="flex-end" mb={8}>
          <Box>
            <Heading size="2xl" color="gray.900" mb={2}>Agendamentos</Heading>
            <Text color="gray.500">Acompanhe e gerencie todas as consultas marcadas.</Text>
          </Box>
          <Button colorPalette="teal" size="lg" shadow="md" rounded="xl">
            <Plus size={18} style={{ marginRight: "6px" }} />
            Agendar Nova
          </Button>
        </Flex>

        {/* Data Grid Glass */}
        <Box bg="white" shadow="xl" rounded="2xl" border="1px solid" borderColor="gray.100" overflow="hidden">
           <Table.Root size="md" variant="line">
            <Table.Header bg="gray.50">
              <Table.Row>
                <Table.ColumnHeader color="gray.600">Protocolo</Table.ColumnHeader>
                <Table.ColumnHeader color="gray.600">Data e Hora</Table.ColumnHeader>
                <Table.ColumnHeader color="gray.600">Médico</Table.ColumnHeader>
                <Table.ColumnHeader color="gray.600">Paciente</Table.ColumnHeader>
                <Table.ColumnHeader color="gray.600">Status</Table.ColumnHeader>
              </Table.Row>
            </Table.Header>
            <Table.Body>
              {loading ? (
                <Table.Row>
                  <Table.Cell colSpan={5} textAlign="center" py={10}>Carregando dados...</Table.Cell>
                </Table.Row>
              ) : appointments.length === 0 ? (
                <Table.Row>
                  <Table.Cell colSpan={5} textAlign="center" py={10} color="gray.500">Nenhum agendamento encontrado.</Table.Cell>
                </Table.Row>
              ) : (
                appointments.map(app => (
                  <Table.Row key={app.id} _hover={{ bg: "gray.50" }} transition="background 0.2s">
                    <Table.Cell fontWeight="medium" color="gray.900">#{app.id}</Table.Cell>
                    <Table.Cell color="gray.600">
                      {new Date(app.startTime).toLocaleString('pt-BR')} - {new Date(app.endTime).toLocaleTimeString('pt-BR', {hour: '2-digit', minute:'2-digit'})}
                    </Table.Cell>
                    <Table.Cell>
                      <HStack gap={2}><User size={14} color="gray" /> <Text>{app.doctorName || "Dr. Desconhecido"}</Text></HStack>
                    </Table.Cell>
                    <Table.Cell>
                       {app.patientName || "Sem Nome"}
                    </Table.Cell>
                    <Table.Cell>
                       <Badge colorPalette="teal" variant="subtle">CONFIRMADO</Badge>
                    </Table.Cell>
                  </Table.Row>
                ))
              )}
            </Table.Body>
          </Table.Root>
        </Box>
      </Container>
    </Box>
  );
}
