package com.abogado3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LawFirmSystem {

	List<Lawyer> lawyers = new ArrayList<>();
	List<Case> cases = new ArrayList<>();

	public void hireA(String name, String lastName, String license) {
		Lawyer lawyer = new Lawyer(name, lastName, license);
		lawyers.add(lawyer);
	}

	public void grantReadAccess(Lawyer caseManager, Lawyer lawyer, String caseId) {

		Case _case = findCase(caseId);
		validateOperation(caseManager, _case);
		_case.grantReadOnlyAccessTo(lawyer);
	}

	public void grantFullAccess(Lawyer caseManager, Lawyer lawyer, String caseId) {

		Case _case = findCase(caseId);
		validateOperation(caseManager, _case);
		_case.grantFullAccessTo(lawyer);
	}

	public void denyAccess(Lawyer caseManager, Lawyer lawyer, String caseId) {

		Case _case = findCase(caseId);
		validateOperation(caseManager, _case);
		_case.denyAccessTo(lawyer);
	}

	public void acceptCase(PotencialCase potencialCase) {
		Lawyer lawyer = findAvailableLawyer();
		assignCaseTo(potencialCase, lawyer);
	}

	public List<Case> findCasesFor(Lawyer lawyer) {
		return findCasesWith(_case -> _case.hasReadOnlyAccessFor(lawyer));
	}

	public List<Case> findCasesWithFullAccessFor(Lawyer lawyer) {
		return findCasesWith(_case -> _case.hasFullAccessFor(lawyer));
	}

	public void grantGlobalReadOnlyAccess(Lawyer caseManager, Lawyer lawyer) {

		List<Case> cases = findCasesManagedBy(caseManager);
		cases.forEach(c -> c.grantReadOnlyAccessTo(lawyer));
	}

	public void grantGlobalFullAccess(Lawyer caseManager, Lawyer lawyer) {
		List<Case> cases = findCasesManagedBy(caseManager);
		cases.forEach(c -> c.grantFullAccessTo(lawyer));
	}

	public void grantGlobalDenyAccess(Lawyer caseManager, Lawyer lawyer) {
		List<Case> cases = findCasesManagedBy(caseManager);
		cases.forEach(c -> c.denyAccessTo(lawyer));
	}

	public List<Lawyer> findLawyersFor(String caseId) {

		Case _case = findCase(caseId);
		List<Lawyer> lawyers = _case.findLawyersWithAccess();
		return lawyers;
	}

	private List<Case> findCasesManagedBy(Lawyer caseManager) {
		return findCasesWith(_case -> _case.isManagedBy(caseManager));
	}

	public Lawyer findLawyer(String license) {

		try {
			Lawyer lawyer = lawyers.stream().filter(l -> l.getLicense().equals(license)).findFirst().get();
			return lawyer;
		} catch (Exception e) {
			throw new RuntimeException("El abogado matricula: " + license + " no pertenece al estudio");
		}
	}

	private Lawyer findAvailableLawyer() {

		if (lawyers.size() < 0)
			throw new RuntimeException("No hay abogados disponibles");

		// Debido a que no hay criterio particular para seleccionar abogado se
		// define arbitrariamente que se toma el primer abogado del pool
		Lawyer abogado = lawyers.get(0);
		return abogado;
	}

	private void assignCaseTo(PotencialCase casoPotencial, Lawyer abogado) {
		Case caso = Case.create(casoPotencial, abogado);
		cases.add(caso);
	}

	private void validateOperation(Lawyer authorizer, Case _case) {
		if (!_case.isManagedBy(authorizer))
			throw new RuntimeException(
					"No tiene permisos para realizar esta operacion. Contactase con el abogado que maneja el caso");
	}

	private Case findCase(String caseId) {

		try {
			Case _case = cases.stream().filter(c -> c.getId().equals(caseId)).findFirst().get();
			return _case;
		} catch (Exception e) {
			throw new RuntimeException("No existe ningun caso para el ID especificado");
		}
	}

	private List<Case> findCasesWith(Predicate<? super Case> predicate) {
		List<Case> filteredCases = cases.stream().filter(predicate).collect(Collectors.<Case> toList());
		return filteredCases;
	}
}
