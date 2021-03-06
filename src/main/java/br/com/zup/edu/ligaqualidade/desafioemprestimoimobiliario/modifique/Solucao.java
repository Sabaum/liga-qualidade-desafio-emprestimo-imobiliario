package br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique;

import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.exception.UnexpectedException;
import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.model.Proposal;
import br.com.zup.edu.ligaqualidade.desafioemprestimoimobiliario.modifique.usecase.ProposalValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solucao {

    private static final String UNEXPECTED_EVENT_MESSAGE = "Unexpected event";
    private static final String INVALID_TYPE_OR_ACTION = "Invalid Type/Action";

    public static String processMessages(List<String> messages) {

        Map<String, Proposal> proposalsMap = new HashMap<>();
        List<String> validProposals = new ArrayList<>();
        List<Proposal> proposals = new ArrayList<>();

        for (String message : messages) {
            String[] parsedMessage = message.split(",");
            String eventType = parsedMessage[1];
            String eventAction = parsedMessage[2];
            String proposalId = parsedMessage[4];

            Event event = Event.getFromTypeAndAction(eventType, eventAction);

            switch (event) {
                case PROPOSAL_CREATED:
                    Proposal proposal = createProposal(proposalId, parsedMessage[5], parsedMessage[6]);
                    proposalsMap.put(proposalId, proposal);
                    proposals.add(proposal);
                    break;
                case WARRANTY_ADDED:
                    addWarrantyToProposal(proposalsMap.get(proposalId), parsedMessage[6], parsedMessage[7]);
                    break;
                case PROPONENT_ADDED:
                    addProponentToProposal(proposalsMap.get(proposalId), parsedMessage[7], parsedMessage[8], parsedMessage[9]);
                    break;
                default:
                    throw new UnexpectedException(UNEXPECTED_EVENT_MESSAGE);
            }
        }

        for (Proposal proposal : proposals) {
            if (ProposalValidator.isValid(proposal)) {
                validProposals.add(proposal.getId());
            }
        }

        return String.join(",", validProposals);
    }

    private static void addProponentToProposal(Proposal proposal, String proponentAge, String proponentIncome, String isMainProposal) {
        proposal.addProponent(Integer.valueOf(proponentAge), new BigDecimal(proponentIncome), Boolean.parseBoolean(isMainProposal));
    }

    private static void addWarrantyToProposal(Proposal proposal, String warrantyValue, String warratyProvince) {
        proposal.addWarranty(new BigDecimal(warrantyValue), warratyProvince);
    }

    private static Proposal createProposal(String proposalId, String proposalValue, String installments) {
        return new Proposal(proposalId, new BigDecimal(proposalValue), Integer.parseInt(installments));
    }

    private enum Event {

        PROPOSAL_CREATED,
        PROPOSAL_UPDATED,
        PROPOSAL_DELETED,
        WARRANTY_ADDED,
        WARRANTY_UPDATED,
        WARRANTY_REMOVED,
        PROPONENT_ADDED;

        static Event getFromTypeAndAction(String type, String action) {
            for (Event event : values()) {
                if (event.name().equalsIgnoreCase(String.format("%s_%s", type, action))) {
                    return event;
                }
            }
            throw new UnexpectedException(INVALID_TYPE_OR_ACTION);
        }
    }
}