package com.masoud.service.order;

import com.masoud.comman.exseptions.NotFoundExceptions;
import com.masoud.comman.exseptions.ValidationExceptions;
import com.masoud.dataaccess.entity.order.Invoice;
import com.masoud.dataaccess.entity.order.InvoiceItem;
import com.masoud.dataaccess.enums.OrderStatus;
import com.masoud.dataaccess.repository.order.InvoiceItemRepository;
import com.masoud.dataaccess.repository.order.InvoiceRepository;
import com.masoud.dto.order.InvoiceDto;
import com.masoud.dto.product.ProductDto;
import com.masoud.service.base.CreateService;
import com.masoud.service.base.HasValidation;
import com.masoud.service.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService implements CreateService<InvoiceDto>, HasValidation<InvoiceDto> {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final ProductService productService;
    private final ModelMapper mapper;
@Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository, ProductService productService, ModelMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    this.productService = productService;
    this.mapper = mapper;
}

    @Override
    public InvoiceDto create(InvoiceDto dto) throws Exception {
        checkValidation(dto);
        Invoice invoice= mapper.map(dto,Invoice.class);
        invoice.setCreateDate(LocalDateTime.now());
        invoice.setOrderStatus(OrderStatus.InProgress);
        invoice.setPayedDate(null);
        long totalAmount = 0L;
        if(invoice.getInvoiceItems()!=null&& invoice.getInvoiceItems().size()>0)
        {

            for(InvoiceItem ii:invoice.getInvoiceItems())
            {
                ProductDto productdto =productService.readById(ii.getProduct().getId());
                ii.setPrice(productdto.getPrice());
                totalAmount += productdto.getPrice()*ii.getQuantity();

            }


        }
        invoice.setTotalAmount(totalAmount);
        Invoice savedinvoice=invoiceRepository.save(invoice);
        return mapper.map(savedinvoice,InvoiceDto.class);





    }

    public List<InvoiceDto> readAllByUserid(long userid)
    {
        return invoiceRepository.findAllByUser_Id(userid).stream().map(x->mapper.map(x,InvoiceDto.class)).toList();

    }

    public InvoiceDto read(Long id) throws NotFoundExceptions {
       Invoice data=invoiceRepository.findById(id).orElseThrow(NotFoundExceptions::new);
       return mapper.map(data,InvoiceDto.class);

    }

    @Override
    public void checkValidation(InvoiceDto invoiceDto) throws ValidationExceptions {

    }
}
