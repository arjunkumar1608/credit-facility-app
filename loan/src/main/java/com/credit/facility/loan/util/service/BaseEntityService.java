package com.credit.facility.loan.util.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.credit.facility.loan.base.entity.BaseAdditionalFields;
import com.credit.facility.loan.base.entity.BaseEntity;
import com.credit.facility.loan.util.enums.ErrorMessage;
import com.credit.facility.loan.util.exception.ItemNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

	private final D dao;

	private static final Integer DEFAULT_PAGE = 0;
	private static final Integer DEFAULT_SIZE = 10;

	public List<E> findAll() {

		return dao.findAll();
	}

	public Optional<E> findById(Long id) {

		return dao.findById(id);
	}

	public E save(E entity) {
		setAdditionalFields(entity);
		entity = dao.save(entity);
		return entity;
	}

	private void setAdditionalFields(E entity) {

		BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

		if (baseAdditionalFields == null) {

			baseAdditionalFields = new BaseAdditionalFields();
			entity.setBaseAdditionalFields(baseAdditionalFields);
		}

		baseAdditionalFields.setUpdateDate(new Date());
	}

	public void delete(E entity) {
		dao.delete(entity);
	}

	public E getByIdWithControl(Long id) {

		Optional<E> entityOptional = findById(id);

		E entity;
		if (entityOptional.isPresent()) {
			entity = entityOptional.get();
		} else {
			throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
		}

		return entity;
	}

	protected PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
		Integer page = getPage(pageOptional);
		Integer size = getSize(sizeOptional);

		PageRequest pageRequest = PageRequest.of(page, size);
		return pageRequest;
	}

	protected Integer getSize(Optional<Integer> sizeOptional) {

		Integer size = DEFAULT_SIZE;
		if (sizeOptional.isPresent()) {
			size = sizeOptional.get();
		}
		return size;
	}

	protected Integer getPage(Optional<Integer> pageOptional) {

		Integer page = DEFAULT_PAGE;
		if (pageOptional.isPresent()) {
			page = pageOptional.get();
		}
		return page;
	}

	public boolean existsById(Long id) {
		return dao.existsById(id);
	}

	public D getDao() {
		return dao;
	}


	public Long getCustomerId() {
		Long customerId = new Random().nextLong(); 
		return customerId;
	}
}
