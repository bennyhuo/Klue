Pod::Spec.new do |s|
  s.name             = '{{wrapperFrameworkName}}'
  s.version          = '0.1.0'
  s.homepage         = 'Link to the Shared Module homepage'
  s.source           = { :http=> ''}
  s.authors          = ''
  s.license          = ''
  s.summary          = 'A short description of {{wrapperFrameworkName}}.'
  s.description      = 'A detailed description of {{wrapperFrameworkName}}.'
  s.license          = ''
  s.ios.deployment_target = '10.0'

  s.source_files = '{{wrapperSourceDir}}'

  s.dependency '{{frameworkName}}'
  s.dependency 'React-Core'
end
