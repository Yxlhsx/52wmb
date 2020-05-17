package Dao;

import Pojo.Companies;

import java.util.List;

public interface CompanieDao {
    List<Companies> getCompanies(String index);

    void addCompanies(Companies companies);
}
