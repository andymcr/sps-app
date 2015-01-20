package org.salephoto.salephotographicsociety.repository;

public class RepositoryProvider {
    private static RepositoryService repository;

    public static RepositoryService getRepository() {
        if (repository == null) {
            repository = new RepositoryService();
        }

        return repository;
    }

}
