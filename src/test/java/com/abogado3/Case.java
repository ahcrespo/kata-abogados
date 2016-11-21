package com.abogado3;

import java.util.ArrayList;
import java.util.List;

public class Case {

	private String id;
	private Lawyer manager;
	private List<Lawyer> lawyersWithReadOnlyAccess = new ArrayList<>();
	private List<Lawyer> lawyersWithFullAccess = new ArrayList<>();
	private List<Lawyer> lawyersWithAccessDenied = new ArrayList<>();

	public static Case create(PotencialCase casoPotencial, Lawyer lawyer) {
		Case caso = new Case(casoPotencial, lawyer);
		caso.initCase();
		return caso;
	}

	private Case(PotencialCase potencialCase, Lawyer lawyer) {
		this.id = potencialCase.getId();
		this.manager = lawyer;
	}

	private void initCase() {
		lawyersWithReadOnlyAccess.add(manager);
		lawyersWithFullAccess.add(manager);
	}

	public boolean isManagedBy(Lawyer lawyer) {
		return manager.equals(lawyer);
	}

	public void grantReadOnlyAccessTo(Lawyer lawyer) {

		addReadOnlyAccessFor(lawyer);
		removeAccessDeniedIfExists(lawyer);
		removeFullAccessIfExists(lawyer);
	}

	public void grantFullAccessTo(Lawyer lawyer) {

		if (!hasReadOnlyAccessFor(lawyer))
			grantReadOnlyAccessTo(lawyer);

		addFullAccessFor(lawyer);
	}

	public void denyAccessTo(Lawyer lawyer) {

		// TODO: Faltan todas las validaciones para que no meta duplicados
		// ejecutando dos veces un permiso para un mismo usuario. Seguramente
		// haya que usar sets o meter if por cada permiso

		removeReadAccessIfExists(lawyer);
		addAccessDeniedFor(lawyer);
		removeFullAccessIfExists(lawyer);
	}

	public boolean hasFullAccessFor(Lawyer lawyer) {
		return hasReadOnlyAccessFor(lawyer) && lawyersWithFullAccess.contains(lawyer);
	}

	public boolean hasAccessDeniedFor(Lawyer lawyer) {
		return lawyersWithAccessDenied.contains(lawyer);
	}

	public List<Lawyer> findLawyersWithAccess() {
		return lawyersWithReadOnlyAccess;
	}

	public boolean hasReadOnlyAccessFor(Lawyer lawyer) {
		return lawyersWithReadOnlyAccess.contains(lawyer);
	}

	public Lawyer getManager() {
		return manager;
	}

	public String getId() {
		return id;
	}

	private void removeReadAccessIfExists(Lawyer lawyer) {
		if (hasReadOnlyAccessFor(lawyer))
			removeReadAccessFor(lawyer);
	}

	private void addReadOnlyAccessFor(Lawyer lawyer) {
		lawyersWithReadOnlyAccess.add(lawyer);
	}

	private void addFullAccessFor(Lawyer lawyer) {
		lawyersWithFullAccess.add(lawyer);
	}

	private void addAccessDeniedFor(Lawyer lawyer) {
		lawyersWithAccessDenied.add(lawyer);
	}

	private void removeFullAccessIfExists(Lawyer lawyer) {
		if (lawyersWithFullAccess.contains(lawyer))
			lawyersWithFullAccess.remove(lawyer);
	}

	private void removeReadAccessFor(Lawyer lawyer) {
		lawyersWithReadOnlyAccess.remove(lawyer);
	}

	private void removeAccessDeniedIfExists(Lawyer lawyer) {
		if (hasAccessDeniedFor(lawyer))
			lawyersWithAccessDenied.remove(lawyer);
	}

}
