import { Client } from '@stomp/stompjs';

const stompClient = new Client({
    brokerURL: "ws://localhost:8080/ws-chat", // 🔥 Connexion STOMP WebSocket
    debug: (str) => console.log(str), // ✅ Debug STOMP
    reconnectDelay: 5000, // ✅ Auto-reconnexion en cas de coupure
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});

stompClient.onConnect = (frame) => {
    console.log("✅ Connecté au WebSocket STOMP");

    // 🔥 Souscription pour recevoir des messages privés
    stompClient.subscribe("/user/queue/messages", (message) => {
        console.log("📩 Message privé reçu :", JSON.parse(message.body));
    });

    // 🔥 Souscription pour recevoir les messages publics
    stompClient.subscribe("/topic/messages", (message) => {
        console.log("📢 Message public reçu :", JSON.parse(message.body));
    });

    // 🔥 Envoi d'un message
    stompClient.publish({
        destination: "/app/send",
        body: JSON.stringify({
            sender: "didi",
            receiver: "KAKA",
            content: "Salut KAKA, comment ça va ?"
        })
    });
};

stompClient.onStompError = (frame) => {
    console.error("❌ Erreur STOMP :", frame);
};

stompClient.activate(); // 🔥 Démarre la connexion STOMP
