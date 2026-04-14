import { Box, Container, Heading, Text, Flex, Button, VStack, HStack, Icon } from "@chakra-ui/react";
import { Link } from "react-router-dom";
import { CalendarHeart, Activity, Lock } from "lucide-react";

export function Landing() {
  return (
    <Box bg="gray.50" minH="100vh" fontFamily="'Inter', sans-serif">
      {/* Navigation */}
      <Flex as="nav" align="center" justify="space-between" px={10} py={6} bg="white/80" backdropFilter="blur(16px)" borderBottom="1px solid" borderColor="gray.100" position="sticky" top={0} zIndex={50}>
        <HStack gap={3}>
          <Icon color="teal.500"><Activity size={28} /></Icon>
          <Heading size="lg" color="gray.800" letterSpacing="tight">MedAgenda</Heading>
        </HStack>
        <HStack gap={4}>
          <Button variant="ghost" colorScheme="teal" asChild>
             <Link to="/login">Entrar</Link>
          </Button>
          <Button colorPalette="teal" asChild rounded="full" px={8} shadow="md">
            <Link to="/login">Área do Médico</Link>
          </Button>
        </HStack>
      </Flex>

      {/* Hero Section */}
      <Container maxW="7xl" pt={{ base: 20, md: 32 }} pb={{ base: 16, md: 24 }}>
        <Flex direction={{ base: "column", lg: "row" }} align="center" gap={16}>
          <VStack align="flex-start" gap={8} flex="1">
            <Box px={4} py={1} bg="teal.50" color="teal.600" rounded="full" fontSize="sm" fontWeight="semibold" border="1px solid" borderColor="teal.100">
              Nova experiência de saúde
            </Box>
            <Heading size="4xl" lineHeight="1.1" color="gray.900" letterSpacing="tighter">
              Agendamento Inteligente para uma Vida Saudável.
            </Heading>
            <Text fontSize="xl" color="gray.500" maxW="2xl" lineHeight="relaxed">
              Descubra a plataforma definitiva que conecta pacientes e doutores sem atrito. Zero conflitos de horário. Foco 100% no seu bem-estar.
            </Text>
            
            <HStack gap={6} pt={4}>
              <Button size="xl" asChild colorPalette="teal" rounded="full" px={10} shadow="xl" _hover={{ transform: "translateY(-2px)", shadow: "2xl" }} transition="all 0.2s">
                <Link to="/login">Agendar Consulta <CalendarHeart style={{ marginLeft: "8px" }} /></Link>
              </Button>
              <Button size="xl" variant="outline" rounded="full" px={10} colorScheme="gray">
                Saiba Mais
              </Button>
            </HStack>
          </VStack>

          <Box flex="1" position="relative" w="full">
            <Box position="absolute" top="-10%" left="-10%" right="-10%" bottom="-10%" bgGradient="to-br" gradientFrom="teal.200" gradientTo="blue.100" filter="blur(60px)" opacity={0.6} rounded="full" zIndex={0} />
            <Box bg="white/60" backdropFilter="blur(20px)" p={8} rounded="3xl" shadow="2xl" border="1px solid" borderColor="white" position="relative" zIndex={1}>
              <VStack gap={6} align="stretch">
                <Flex align="center" gap={4} p={4} bg="white" rounded="xl" shadow="sm">
                  <Box p={3} bg="teal.50" rounded="lg" color="teal.500"><Lock size={24} /></Box>
                  <Box>
                    <Text fontWeight="bold" color="gray.800">Segurança Criptografada</Text>
                    <Text fontSize="sm" color="gray.500">Seus dados blindados com JWT</Text>
                  </Box>
                </Flex>
                <Flex align="center" gap={4} p={4} bg="white" rounded="xl" shadow="sm">
                  <Box p={3} bg="blue.50" rounded="lg" color="blue.500"><Activity size={24} /></Box>
                  <Box>
                    <Text fontWeight="bold" color="gray.800">Anti-Conflito de Horário</Text>
                    <Text fontSize="sm" color="gray.500">Sem duplo agendamento, nunca.</Text>
                  </Box>
                </Flex>
              </VStack>
            </Box>
          </Box>
        </Flex>
      </Container>
    </Box>
  );
}
